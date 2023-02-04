package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.AuthorisationFragment
import com.example.myapplication.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var mSettings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (savedInstanceState != null) {
            return
        }

        binding?.run {
            if (getFromSharedPreferences() != null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MainFragment(), MainFragment.MAIN_FRAGMENT_TAG)
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, AuthorisationFragment(), AuthorisationFragment.AUTHORISATION_FRAGMENT_TAG)
                    .commit()
            }
        }
    }

    fun getFromSharedPreferences(): String? =
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(KEY, null)

    fun putToSharedPreferences(value: String) {
        mSettings?.edit {
            putString(KEY, value)
            apply()
        }
    }

    fun clearSharedPreferences() {
        mSettings?.edit {
            clear()
        }
    }

    companion object {
        const val APP_PREFERENCES = "mysettings"
        const val KEY = "email"
    }
}