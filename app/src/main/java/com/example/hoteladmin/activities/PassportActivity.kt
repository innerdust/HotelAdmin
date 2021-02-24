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
import com.example.hoteladmin.databinding.ActivityPassportBinding
import com.example.hoteladmin.db.HotelDatabase
import com.example.hoteladmin.db.entities.Passport
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.PassportViewModel
import com.example.hoteladmin.viewmodel.viewmodelfactory.PassportViewModelFactory

class PassportActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityPassportBinding
    private lateinit var passportViewModel: PassportViewModel
    private lateinit var adapter: PassportAdapter
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passport)
        nextButton = findViewById<Button>(R.id.next_btn_passport)
        prevButton = findViewById<Button>(R.id.prev_btn_passport)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_passport)
        val dao = HotelDatabase.getInstance(application).hotelDao
        val repository = HotelRepository(dao)
        val factory =
            PassportViewModelFactory(
                repository
            )
        passportViewModel = ViewModelProvider(this, factory).get(PassportViewModel::class.java)
        binding.myPassportViewModel = passportViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        passportViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        nextButton.setOnClickListener(this)
        prevButton.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.passportRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PassportAdapter({ selectedItem: Passport -> listItemClicked(selectedItem) })
        binding.passportRecyclerView.adapter = adapter
        displayHotelsList()
    }

    private fun displayHotelsList() {
        passportViewModel.passport.observe(this, Observer {
            Log.i("MyTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(passport: Passport) {
        //Toast.makeText(this, "selected name of hotel is ${hotel.name}", Toast.LENGTH_LONG).show()
        passportViewModel.initUpdateAndDelete(passport)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, ManagerActivity::class.java)
        startActivity(intent)
    }

    fun onClick2(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}