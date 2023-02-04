package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.adapter.CheckboxAdapter
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.entity.User
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.model.CheckboxRepository
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var binding: FragmentSettingsBinding? = null

    private var repository: UserRepository? = null

    private var adapter: CheckboxAdapter? = null

    private var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        repository = UserRepository(requireContext())

        val userEmail = (activity as MainActivity).getFromSharedPreferences()

        binding?.run {
            lifecycleScope.launch {
                user = repository?.getUserByEmail(userEmail!!)
                adapter = CheckboxAdapter(CheckboxRepository.checkboxes, user!!, this@SettingsFragment)
                rvCheckbox.adapter = adapter
                rvCheckbox.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    fun updateUserCheckboxesData(position: Int) {
        println(position)
        when (position) {
            0 -> user!!.checkbox1 = !user!!.checkbox1
            1 -> user!!.checkbox2 = !user!!.checkbox2
            2 -> user!!.checkbox3 = !user!!.checkbox3
        }
        lifecycleScope.launch {
            repository?.update(user!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        repository = null
    }
}