package com.example.hoteladmin.viewmodel.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteladmin.db.entities.Manager
import com.example.hoteladmin.db.entities.Passport
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.event.Event
import kotlinx.coroutines.launch

class ManagerViewModel(private val repository: HotelRepository) : ViewModel(), Observable {
    val manager = repository.managers
    private var isUpdateOrDelete = false
    private lateinit var managerToUpdateOrDelete: Manager


    @Bindable
    val fullnameManager = MutableLiveData<String>()
    @Bindable
    val addressManager = MutableLiveData<String>()
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
            managerToUpdateOrDelete.fullname = fullnameManager.value!!
            managerToUpdateOrDelete.address = addressManager.value!!
            update(managerToUpdateOrDelete)
        } else {
            val fullname = fullnameManager.value!!
            val address = addressManager.value!!
            insert(Manager(0, fullname, address))
            fullnameManager.value = null
            addressManager.value = null
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete) {
            delete(managerToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(manager: Manager) = viewModelScope.launch {
        val newRowId = repository.insert(manager)
        if(newRowId > -1) {
            statusMessage.value = Event("Manager inserted successfully $newRowId")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun update(manager: Manager) = viewModelScope.launch {
        val noOfRows = repository.update(manager)
        if (noOfRows > 0) {
            fullnameManager.value = null
            addressManager.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Manager updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(manager: Manager) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(manager)
        if (noOfRowsDeleted > 0) {
            fullnameManager.value = null
            addressManager.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Manager deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAllManager()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("All managers deleted successfully")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun initUpdateAndDelete(manager: Manager) {
        fullnameManager.value = manager.fullname
        addressManager.value = manager.address
        isUpdateOrDelete = true
        managerToUpdateOrDelete = manager
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}