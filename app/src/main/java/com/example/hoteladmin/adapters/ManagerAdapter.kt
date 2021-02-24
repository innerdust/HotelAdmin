package com.example.hoteladmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteladmin.R
import com.example.hoteladmin.databinding.ListItemBinding
import com.example.hoteladmin.databinding.ListItemManagerBinding
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.entities.Manager

class ManagerAdapter(private val clickListener: (Manager) -> Unit)
    : RecyclerView.Adapter<ManagerViewHolder>() {

    private val managersList = ArrayList<Manager>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemManagerBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_manager, parent, false)
        return ManagerViewHolder(binding)
    }

    override fun getItemCount(): Int = managersList.size

    override fun onBindViewHolder(holder: ManagerViewHolder, position: Int) {
        holder.bind(managersList[position], clickListener)
    }

    fun setList(manager: List<Manager>) {
        managersList.clear()
        managersList.addAll(manager)

    }

}

class ManagerViewHolder(val binding: ListItemManagerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(manager: Manager, clickListener: (Manager) -> Unit) {
        binding.fullnameTextView.text = manager.fullname
        binding.addressTextView.text = manager.address
        binding.listItemLayout.setOnClickListener {
            clickListener(manager)
        }
    }
}