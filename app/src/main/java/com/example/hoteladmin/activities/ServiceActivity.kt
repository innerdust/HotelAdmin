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
import com.example.hoteladmin.adapters.PassportAdapter
import com.example.hoteladmin.adapters.ServiceAdapter
import com.example.hoteladmin.databinding.ActivityPassportBinding
import com.example.hoteladmin.databinding.ActivityServiceBinding
import com.example.hoteladmin.db.HotelDatabase
import com.example.hoteladmin.db.entities.Passport
import com.example.hoteladmin.db.entities.Service
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.PassportViewModel
import com.example.hoteladmin.viewmodel.model.ServiceViewModel
import com.example.hoteladmin.viewmodel.viewmodelfactory.PassportViewModelFactory
import com.example.hoteladmin.viewmodel.viewmodelfactory.ServiceViewModelFactory

class ServiceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityServiceBinding
    private lateinit var passportViewModel: ServiceViewModel
    private lateinit var adapter: ServiceAdapter
    private lateinit var prevButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        prevButton = findViewById<Button>(R.id.prev_btn_service)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service)
        val dao = HotelDatabase.getInstance(application).hotelDao
        val repository = HotelRepository(dao)
        val factory =
                ServiceViewModelFactory(
                        repository
                )
        passportViewModel = ViewModelProvider(this, factory).get(ServiceViewModel::class.java)
        binding.myServiceViewModel = passportViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        passportViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        prevButton.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.passportRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ServiceAdapter({ selectedItem: Service -> listItemClicked(selectedItem) })
        binding.passportRecyclerView.adapter = adapter
        displayHotelsList()
    }

    private fun displayHotelsList() {
        passportViewModel.service.observe(this, Observer {
            Log.i("MyTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(service: Service) {
        //Toast.makeText(this, "selected name of hotel is ${hotel.name}", Toast.LENGTH_LONG).show()
        passportViewModel.initUpdateAndDelete(service)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, CustomerActivity::class.java)
        startActivity(intent)
    }

}