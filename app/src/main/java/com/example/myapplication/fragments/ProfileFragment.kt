package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.entity.User
import com.example.myapplication.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding? = null

    private var repository: UserRepository? = null

    private val handler = CoroutineExceptionHandler { _, _ ->
        Toast.makeText(context, "Email занят", Toast.LENGTH_SHORT)
            .show()
    }

    private var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        repository = UserRepository(requireContext())

        var userEmail = (activity as MainActivity).getFromSharedPreferences()
        lifecycleScope.launch {
            user = repository?.getUserByEmail(userEmail!!)
            binding?.run {
                tvName.text = user?.name
                etEmail.setText(userEmail)
            }
        }

        binding?.run {

            ibEdit.setOnClickListener {
                etEmail.isEnabled = true
                btnSave.isEnabled = true
            }

            btnSave.setOnClickListener {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                    Toast.makeText(context, "Некорректный email", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                user?.email = etEmail.text.toString()
                lifecycleScope.launch(handler) {
                    repository?.updateUserEmail(user!!.email, userEmail!!)
                    userEmail = user!!.email
                    (activity as MainActivity).clearSharedPreferences()
                    (activity as MainActivity).putToSharedPreferences(userEmail!!)
                    etEmail.isEnabled = false
                    btnSave.isEnabled = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        repository = null
    }
}