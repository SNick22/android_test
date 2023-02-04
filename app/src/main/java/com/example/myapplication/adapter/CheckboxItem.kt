package com.example.myapplication.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCheckboxBinding
import com.example.myapplication.fragments.SettingsFragment
import com.example.myapplication.model.Checkbox

class CheckboxItem(
    private var binding: ItemCheckboxBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(checkbox: Checkbox, isChecked: Boolean, fragment: SettingsFragment) {
        binding.run {
            itemCheckbox.text = checkbox.title
            itemCheckbox.isChecked = isChecked

            itemCheckbox.setOnCheckedChangeListener { _, _ ->
                fragment.updateUserCheckboxesData(adapterPosition)
            }
        }
    }
}