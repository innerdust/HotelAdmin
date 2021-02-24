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
import com.example.hoteladmin.adapters.ManagerAdapter
import com.example.hoteladmin.adapters.PassportAdapter
import com.example.hoteladmin.databinding.ActivityManagerBinding
import com.example.hoteladmin.databinding.ActivityPassportBinding
import com.example.hoteladmin.db.HotelDatabase
import com.example.hoteladmin.db.entities.Manager
import com.example.hoteladmin.db.entities.Passport
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.ManagerViewModel
import com.example.hoteladmin.viewmodel.model.PassportViewModel
import com.example.hoteladmin.viewmodel.viewmodelfactory.ManagerViewModelFactory
import com.example.hoteladmin.viewmodel.viewmodelfactory.PassportViewModelFactory

class ManagerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityManagerBinding
    private lateinit var managerViewModel: ManagerViewModel
    private lateinit var adapter: ManagerAdapter
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)
        nextButton = findViewById<Button>(R.id.next_btn_manager)
        prevButton = findViewById<Button>(R.id.prev_btn_manager)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manager)
        val dao = HotelDatabase.getInstance(application).hotelDao
        val repository = HotelRepository(dao)
        val factory =
            ManagerViewModelFactory(
                repository
            )
        managerViewModel = ViewModelProvider(this, factory).get(ManagerViewModel::class.java)
        binding.myManagerViewModel = managerViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        managerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        nextButton.setOnClickListener(this)
        prevButton.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.managerRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ManagerAdapter({ selectedItem: Manager -> listItemClicked(selectedItem) })
        binding.managerRecyclerView.adapter = adapter
        displayHotelsList()
    }

    private fun displayHotelsList() {
        managerViewModel.manager.observe(this, Observer {
            Log.i("MyTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(manager: Manager) {
        //Toast.makeText(this, "selected name of hotel is ${hotel.name}", Toast.LENGTH_LONG).show()
        managerViewModel.initUpdateAndDelete(manager)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, EmployeeActivity::class.java)
        startActivity(intent)
    }

    fun onClick3(view: View?) {
        val intent = Intent(this, PassportActivity::class.java)
        startActivity(intent)
    }
}