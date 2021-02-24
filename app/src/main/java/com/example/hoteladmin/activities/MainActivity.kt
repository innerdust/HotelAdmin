package com.example.hoteladmin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoteladmin.R
import com.example.hoteladmin.adapters.MyRecyclerViewAdapter
import com.example.hoteladmin.databinding.ActivityMainBinding
import com.example.hoteladmin.db.HotelDatabase
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.EmployeeViewModel
import com.example.hoteladmin.viewmodel.model.HotelViewModel
import com.example.hoteladmin.viewmodel.viewmodelfactory.HotelViewModelFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hotelViewModel: HotelViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var nextButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextButton = findViewById<Button>(R.id.next_btn_hotel)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = HotelDatabase.getInstance(application).hotelDao
        val repository = HotelRepository(dao)
        val factory =
            HotelViewModelFactory(
                repository
            )
        hotelViewModel = ViewModelProvider(this, factory).get(HotelViewModel::class.java)
        binding.myViewModel = hotelViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        hotelViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        nextButton.setOnClickListener(this)

    }


    private fun initRecyclerView() {
        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({ selectedItem: Hotel -> listItemClicked(selectedItem) })
        binding.hotelRecyclerView.adapter = adapter
        displayHotelsList()
    }

    private fun displayHotelsList() {
        hotelViewModel.hotels.observe(this, Observer {
            Log.i("MyTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(hotel: Hotel) {
        //Toast.makeText(this, "selected name of hotel is ${hotel.name}", Toast.LENGTH_LONG).show()
        hotelViewModel.initUpdateAndDelete(hotel)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, PassportActivity::class.java)
        startActivity(intent)
    }
}

