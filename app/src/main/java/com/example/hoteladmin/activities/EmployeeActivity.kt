package com.example.hoteladmin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoteladmin.R
import com.example.hoteladmin.adapters.CustomerAdapter
import com.example.hoteladmin.adapters.EmployeeAdapter
import com.example.hoteladmin.databinding.ActivityCustomerBinding
import com.example.hoteladmin.databinding.ActivityEmployeeBinding
import com.example.hoteladmin.db.HotelDatabase
import com.example.hoteladmin.db.entities.Customer
import com.example.hoteladmin.db.entities.Employee
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.CustomerViewModel
import com.example.hoteladmin.viewmodel.model.EmployeeViewModel
import com.example.hoteladmin.viewmodel.viewmodelfactory.CustomerViewModelFactory
import com.example.hoteladmin.viewmodel.viewmodelfactory.EmployeeViewModelFactory

class EmployeeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEmployeeBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var adapter: EmployeeAdapter
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        nextButton = findViewById<Button>(R.id.next_btn_employee)
        prevButton = findViewById<Button>(R.id.prev_btn_employee)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee)
        val dao = HotelDatabase.getInstance(application).hotelDao
        val repository = HotelRepository(dao)
        val factory =
                EmployeeViewModelFactory(
                        repository
                )
        employeeViewModel = ViewModelProvider(this, factory).get(EmployeeViewModel::class.java)
        binding.myEmployeeViewModel = employeeViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        employeeViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        nextButton.setOnClickListener(this)
        prevButton.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeAdapter({ selectedItem: Employee -> listItemClicked(selectedItem) })
        binding.customerRecyclerView.adapter = adapter
        displayHotelsList()
    }

    private fun displayHotelsList() {
        employeeViewModel.employee.observe(this, Observer {
            Log.i("MyTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(employee: Employee) {
        //Toast.makeText(this, "selected name of hotel is ${hotel.name}", Toast.LENGTH_LONG).show()
        employeeViewModel.initUpdateAndDelete(employee)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, CustomerActivity::class.java)
        startActivity(intent)
    }

    fun onClick4(view: View?) {
        val intent = Intent(this, ManagerActivity::class.java)
        startActivity(intent)
    }
}