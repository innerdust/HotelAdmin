package com.example.hoteladmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteladmin.R
import com.example.hoteladmin.databinding.ListItemBinding
import com.example.hoteladmin.databinding.ListItemCustomerBinding
import com.example.hoteladmin.db.entities.Customer
import com.example.hoteladmin.db.entities.Hotel

class CustomerAdapter(private val clickListener: (Customer) -> Unit)
    : RecyclerView.Adapter<CustomerViewHolder>() {

    private val customersList = ArrayList<Customer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemCustomerBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_customer, parent, false)
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int = customersList.size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(customersList[position], clickListener)
    }

    fun setList(customer: List<Customer>) {
        customersList.clear()
        customersList.addAll(customer)

    }

}

class CustomerViewHolder(val binding: ListItemCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(customer: Customer, clickListener: (Customer) -> Unit) {
        binding.fullnameCustomerTextView.text = customer.fullname
        binding.customerPhoneTextView.text = customer.phone
        binding.listItemLayout.setOnClickListener {
            clickListener(customer)
        }
    }
}