package com.example.hoteladmin.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.EmployeeViewModel
import com.example.hoteladmin.viewmodel.model.ServiceViewModel
import java.lang.IllegalArgumentException

class ServiceViewModelFactory(private val repository: HotelRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ServiceViewModel::class.java)) {
            return ServiceViewModel(
                    repository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}