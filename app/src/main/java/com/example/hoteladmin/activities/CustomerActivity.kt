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
import com.example.hoteladmin.adapters.ManagerAdapter
import com.example.hoteladmin.adapters.MyRecyclerViewAdapter
import com.example.hoteladmin.databinding.ActivityCustomerBinding
import com.example.hoteladmin.databinding.ActivityManagerBinding
import com.example.hoteladmin.db.HotelDatabase
import com.example.hoteladmin.db.entities.Customer
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.CustomerViewModel
import com.example.hoteladmin.viewmodel.model.HotelViewModel
import com.example.hoteladmin.viewmodel.model.ManagerViewModel
import com.example.hoteladmin.viewmodel.viewmodelfactory.CustomerViewModelFactory
import com.example.hoteladmin.viewmodel.viewmodelfactory.HotelViewModelFactory


class CustomerActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCustomerBinding
    private lateinit var customerViewModel: CustomerViewModel
    private lateinit var adapter: CustomerAdapter
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        nextButton = findViewById<Button>(R.id.next_btn_customer)
        prevButton = findViewById<Button>(R.id.prev_btn_customer)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer)
        val dao = HotelDatabase.getInstance(application).hotelDao
        val repository = HotelRepository(dao)
        val factory =
            CustomerViewModelFactory(
                repository
            )
        customerViewModel = ViewModelProvider(this, factory).get(CustomerViewModel::class.java)
        binding.myCustomerViewModel = customerViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        customerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        nextButton.setOnClickListener(this)
        prevButton.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomerAdapter({ selectedItem: Customer -> listItemClicked(selectedItem) })
        binding.customerRecyclerView.adapter = adapter
        displayHotelsList()
    }

    private fun displayHotelsList() {
        customerViewModel.customer.observe(this, Observer {
            Log.i("MyTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(customer: Customer) {
        //Toast.makeText(this, "selected name of hotel is ${hotel.name}", Toast.LENGTH_LONG).show()
        customerViewModel.initUpdateAndDelete(customer)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, ServiceActivity::class.java)
        startActivity(intent)
    }

    fun onClick5(view: View?) {
        val intent = Intent(this, EmployeeActivity::class.java)
        startActivity(intent)
    }
}