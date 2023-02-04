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
import com.example.myapplication.databinding.FragmentAuthorisationBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AuthorisationFragment : Fragment(R.layout.fragment_authorisation) {

    private var binding: FragmentAuthorisationBinding? = null
    private var repository: UserRepository? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorisationBinding.bind(view)
        repository = UserRepository(requireContext())

        binding?.run {
            etEmail.setCheck()
            etPassword.setCheck()

            btnSignup.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                    )
                    .replace(R.id.container, RegistrationFragment(), RegistrationFragment.REGISTRATION_FRAGMENT_TAG)
                    .addToBackStack(RegistrationFragment.REGISTRATION_FRAGMENT_TAG)
                    .commit()
            }

            btnSignin.setOnClickListener {
                lifecycleScope.launch {
                    repository?.getUserByEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                        .also {
                            if (it == null) {
                                Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                (activity as MainActivity).putToSharedPreferences(it.email)
                                showMainFragment()
                            }
                        }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        repository = null
    }

    private fun checkEnteredText() {
        binding?.run {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                btnSignin.isEnabled = false
                return
            }
            if (!isPasswordValid(etPassword.text.toString())) {
                btnSignin.isEnabled = false
                return
            }

            btnSignin.isEnabled = true
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

    private fun TextInputEditText.setCheck() = doAfterTextChanged { checkEnteredText() }

    private fun String.isLongEnough() = length >= 6

    private fun String.hasEnoughDigits() = count(Char::isDigit) > 0

    private fun String.hasEnoughLetters() = count(Char::isLetter) > 0

    private fun String.hasSpecialChar() = any { it in "!@#\$%^&*()-_+=;:,./?\\|`~[]{}" }

    private fun isPasswordValid(password: String) =
        password.run { isLongEnough() && hasEnoughLetters() && hasEnoughDigits() && hasSpecialChar() }

    companion object {
        const val AUTHORISATION_FRAGMENT_TAG = "AUTHORISATION_FRAGMENT_TAG"
    }
}