package com.example.hoteladmin.viewmodel.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.viewmodel.model.HotelViewModel
import com.example.hoteladmin.viewmodel.model.PassportViewModel
import java.lang.IllegalArgumentException


class PassportViewModelFactory(private val repository: HotelRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PassportViewModel::class.java)) {
            return PassportViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}