package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.entity.User
import com.example.myapplication.databinding.ItemCheckboxBinding
import com.example.myapplication.fragments.SettingsFragment
import com.example.myapplication.model.Checkbox

class CheckboxAdapter(
    private val list: List<Checkbox>,
    private val user: User,
    private val fragment: SettingsFragment
) : RecyclerView.Adapter<CheckboxItem>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckboxItem = CheckboxItem(
        binding = ItemCheckboxBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: CheckboxItem,
        position: Int
    ) {
        when(position) {
            0 -> holder.onBind(list[position], user.checkbox1, fragment)
            1 -> holder.onBind(list[position], user.checkbox2, fragment)
            2 -> holder.onBind(list[position], user.checkbox3, fragment)
        }
    }

    override fun getItemCount(): Int = list.size
}