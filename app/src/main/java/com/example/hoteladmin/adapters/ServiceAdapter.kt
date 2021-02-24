package com.example.hoteladmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteladmin.R
import com.example.hoteladmin.databinding.ListItemBinding
import com.example.hoteladmin.databinding.ListItemServiceBinding
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.entities.Service

class ServiceAdapter(private val clickListener: (Service) -> Unit)
    : RecyclerView.Adapter<ServiceViewHolder>() {

    private val servicesList = ArrayList<Service>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemServiceBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_service, parent, false)
        return ServiceViewHolder(binding)
    }

    override fun getItemCount(): Int = servicesList.size

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(servicesList[position], clickListener)
    }

    fun setList(services: List<Service>) {
        servicesList.clear()
        servicesList.addAll(services)

    }

}

class ServiceViewHolder(val binding: ListItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(service: Service, clickListener: (Service) -> Unit) {
        binding.nameServiceTextView.text = service.name
        binding.costTextView.text = service.cost
        binding.listItemLayout.setOnClickListener {
            clickListener(service)
        }
    }
}