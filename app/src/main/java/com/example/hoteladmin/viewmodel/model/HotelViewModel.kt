package com.example.hoteladmin.viewmodel.model

import android.content.Intent
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteladmin.activities.PassportActivity
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.event.Event
import kotlinx.coroutines.launch


class HotelViewModel(private val repository: HotelRepository) : ViewModel(), Observable {

    val hotels = repository.hotels
    private var isUpdateOrDelete = false
    private lateinit var hotelToUpdateOrDelete: Hotel

    @Bindable
    val inputNameOfHotel = MutableLiveData<String>()
    @Bindable
    val inputAddress = MutableLiveData<String>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()


    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        if(isUpdateOrDelete) {
            hotelToUpdateOrDelete.name = inputNameOfHotel.value!!
            hotelToUpdateOrDelete.address = inputAddress.value!!
            update(hotelToUpdateOrDelete)
        } else {
            val name = inputNameOfHotel.value!!
            val address = inputAddress.value!!
            insert(Hotel(0, name, address))
            inputNameOfHotel.value = null
            inputNameOfHotel.value = null
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete) {
            delete(hotelToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(hotel : Hotel) = viewModelScope.launch {
            val newRowId = repository.insert(hotel)
            if(newRowId > -1) {
                statusMessage.value = Event("Hotel inserted successfully $newRowId")
            } else {
                statusMessage.value = Event("Error")
            }
        }

    fun update(hotel: Hotel) = viewModelScope.launch {
        val noOfRows = repository.update(hotel)
        if (noOfRows > 0) {
            inputNameOfHotel.value = null
            inputAddress.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Hotel updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(hotel: Hotel) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(hotel)
        if (noOfRowsDeleted > 0) {
            inputNameOfHotel.value = null
            inputAddress.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Hotel deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("All hotels deleted successfully")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun initUpdateAndDelete(hotel: Hotel) {
        inputNameOfHotel.value = hotel.name
        inputAddress.value = hotel.address
        isUpdateOrDelete = true
        hotelToUpdateOrDelete = hotel
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}