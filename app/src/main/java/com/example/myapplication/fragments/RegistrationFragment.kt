package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.entity.User
import com.example.myapplication.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private var binding: FragmentRegistrationBinding? = null

    private val handler = CoroutineExceptionHandler { _, _ ->
        Toast.makeText(context, "Email занят", Toast.LENGTH_SHORT)
            .show()
    }

    private var repository: UserRepository? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        repository = UserRepository(requireContext())

        binding?.run {
            etName.setCheck()
            etEmail.setCheck()
            etPassword.setCheck()
            etPasswordRepeat.setCheck()

            btnSignup.setOnClickListener {
                val user = User(
                    name = etName.text.toString(),
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString()
                )

                lifecycleScope.launch(handler) {
                    repository?.saveUser(user)
                    (activity as MainActivity).putToSharedPreferences(user.email)
                    showMainFragment()
                }
            }
        }
    }

    private fun showMainFragment() {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
            )
            .replace(R.id.container, MainFragment(), MainFragment.MAIN_FRAGMENT_TAG)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        repository = null
    }

    private fun checkEnteredText() {
        binding?.run {
            if (etName.text.toString() == "") {
                btnSignup.isEnabled = false
                return
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                btnSignup.isEnabled = false
                return
            }
            if (!isPasswordValid(etPassword.text.toString())) {
                btnSignup.isEnabled = false
                return
            }
            if (etPasswordRepeat.text.toString() != etPassword.text.toString()) {
                btnSignup.isEnabled = false
                return
            }

            btnSignup.isEnabled = true
        }
    }

    private fun TextInputEditText.setCheck() = doAfterTextChanged { checkEnteredText() }

    private fun String.isLongEnough() = length >= 6

    private fun String.hasEnoughDigits() = count(Char::isDigit) > 0

    private fun String.hasEnoughLetters() = count(Char::isLetter) > 0

    private fun String.hasSpecialChar() = any { it in "!@#\$%^&*()-_+=;:,./?\\|`~[]{}" }

    private fun isPasswordValid(password: String) =
        password.run { isLongEnough() && hasEnoughLetters() && hasEnoughDigits() && hasSpecialChar() }

    companion object {
        const val REGISTRATION_FRAGMENT_TAG = "REGISTRATION_FRAGMENT_TAG"
    }
}