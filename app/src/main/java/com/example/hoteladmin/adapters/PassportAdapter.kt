package com.example.hoteladmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteladmin.R
import com.example.hoteladmin.databinding.ListItemBinding
import com.example.hoteladmin.databinding.ListItemPassportBinding
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.entities.Passport

class PassportAdapter(private val clickListener: (Passport) -> Unit)
    : RecyclerView.Adapter<PassportViewHolder>() {

    private val passportsList = ArrayList<Passport>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassportViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemPassportBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_passport, parent, false)
        return PassportViewHolder(binding)
    }

    override fun getItemCount(): Int = passportsList.size

    override fun onBindViewHolder(holder: PassportViewHolder, position: Int) {
        holder.bind(passportsList[position], clickListener)
    }

    fun setList(passports: List<Passport>) {
        passportsList.clear()
        passportsList.addAll(passports)

    }

}

class PassportViewHolder(val binding: ListItemPassportBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(passport: Passport, clickListener: (Passport) -> Unit) {
        binding.seriesTextView.text = passport.series
        binding.numberTextView.text = passport.number
        binding.dateOfBirthTextView.text = passport.date_of_birth
        binding.registeredTextView.text = passport.registered
        binding.listItemLayout.setOnClickListener {
            clickListener(passport)
        }
    }
}