package com.example.hoteladmin.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.HotelViewModel
import java.lang.IllegalArgumentException

class HotelViewModelFactory(private val repository: HotelRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HotelViewModel::class.java)) {
            return HotelViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}