package com.example.myapplication.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.entity.User
import com.example.myapplication.databinding.FragmentMainBinding

class MainFragment() : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding?.run {

            bnvMain.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.main_profile -> {
                        navigateTo(ProfileFragment())
                        true
                    }
                    R.id.main_settings -> {
                        navigateTo(SettingsFragment())
                        true
                    }
                    else -> false
                }
            }
            bnvMain.selectedItemId = R.id.main_profile
        }
    }

    private fun navigateTo(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.inner_container, fragment, fragment.javaClass.name)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.toolbar_exit -> {
                (activity as MainActivity).clearSharedPreferences()
                parentFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                    )
                    .replace(R.id.container, AuthorisationFragment(), AuthorisationFragment.AUTHORISATION_FRAGMENT_TAG)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}