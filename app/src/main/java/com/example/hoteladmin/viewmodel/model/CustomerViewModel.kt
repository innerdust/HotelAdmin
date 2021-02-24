package com.example.hoteladmin.viewmodel.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteladmin.db.entities.Customer
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.event.Event
import kotlinx.coroutines.launch

class CustomerViewModel(private val repository: HotelRepository) : ViewModel(), Observable {

    val customer = repository.customers
    private var isUpdateOrDelete = false
    private lateinit var customerToUpdateOrDelete: Customer


    @Bindable
    val fullnameCustomer = MutableLiveData<String>()
    @Bindable
    val phoneCustomer = MutableLiveData<String>()
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
            customerToUpdateOrDelete.fullname = fullnameCustomer.value!!
            customerToUpdateOrDelete.phone = phoneCustomer.value!!
            update(customerToUpdateOrDelete)
        } else {
            val fullname = fullnameCustomer.value!!
            val phone = phoneCustomer.value!!
            insert(Customer(0, fullname, phone))
            fullnameCustomer.value = null
            phoneCustomer.value = null
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete) {
            delete(customerToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(customer: Customer) = viewModelScope.launch {
        val newRowId = repository.insert(customer)
        if(newRowId > -1) {
            statusMessage.value = Event("Customer inserted successfully $newRowId")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun update(customer: Customer) = viewModelScope.launch {
        val noOfRows = repository.update(customer)
        if (noOfRows > 0) {
            fullnameCustomer.value = null
            phoneCustomer.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Customer updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(customer: Customer) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(customer)
        if (noOfRowsDeleted > 0) {
            fullnameCustomer.value = null
            phoneCustomer.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Customer deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAllCustomer()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("All customers deleted successfully")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun initUpdateAndDelete(customer: Customer) {
        fullnameCustomer.value = customer.fullname
        phoneCustomer.value = customer.phone
        isUpdateOrDelete = true
        customerToUpdateOrDelete = customer
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}