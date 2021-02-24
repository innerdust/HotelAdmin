package com.example.hoteladmin.viewmodel.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.entities.Service
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.event.Event
import kotlinx.coroutines.launch

class ServiceViewModel(private val repository: HotelRepository) : ViewModel(), Observable {

    val service = repository.services
    private var isUpdateOrDelete = false
    private lateinit var serviceToUpdateOrDelete: Service


    @Bindable
    val nameService = MutableLiveData<String>()
    @Bindable
    val costService = MutableLiveData<String>()
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
            serviceToUpdateOrDelete.name = nameService.value!!
            serviceToUpdateOrDelete.cost = costService.value!!
            update(serviceToUpdateOrDelete)
        } else {
            val name = nameService.value!!
            val cost = costService.value!!
            insert(Service(0, name, cost))
            nameService.value = null
            costService.value = null
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete) {
            delete(serviceToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(service: Service) = viewModelScope.launch {
        val newRowId = repository.insert(service)
        if(newRowId > -1) {
            statusMessage.value = Event("Service inserted successfully $newRowId")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun update(service: Service) = viewModelScope.launch {
        val noOfRows = repository.update(service)
        if (noOfRows > 0) {
            nameService.value = null
            costService.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Service updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(service: Service) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(service)
        if (noOfRowsDeleted > 0) {
            nameService.value = null
            costService.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Service deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAllService()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("All services deleted successfully")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun initUpdateAndDelete(service: Service) {
        nameService.value = service.name
        costService.value = service.cost
        isUpdateOrDelete = true
        serviceToUpdateOrDelete = service
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}