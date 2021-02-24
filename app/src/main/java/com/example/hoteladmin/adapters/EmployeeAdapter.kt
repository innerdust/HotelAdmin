package com.example.hoteladmin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoteladmin.R
import com.example.hoteladmin.databinding.ListItemBinding
import com.example.hoteladmin.databinding.ListItemEmployeeBinding
import com.example.hoteladmin.db.entities.Employee
import com.example.hoteladmin.db.entities.Hotel

class EmployeeAdapter(private val clickListener: (Employee) -> Unit)
    : RecyclerView.Adapter<EmployeeViewHolder>() {

    private val employeesList = ArrayList<Employee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemEmployeeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_employee, parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun getItemCount(): Int = employeesList.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employeesList[position], clickListener)
    }

    fun setList(employees: List<Employee>) {
        employeesList.clear()
        employeesList.addAll(employees)

    }

}

class EmployeeViewHolder(val binding: ListItemEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(employee: Employee, clickListener: (Employee) -> Unit) {
        binding.fullnameEmployeeTextView.text = employee.fullname
        binding.phoneEmployeeTextView.text = employee.phone
        binding.salaryTextView.text = employee.salary
        binding.listItemLayout.setOnClickListener {
            clickListener(employee)
        }
    }
}