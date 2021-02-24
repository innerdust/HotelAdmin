package com.example.hoteladmin.viewmodel.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteladmin.db.entities.Employee
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.event.Event
import kotlinx.coroutines.launch

class EmployeeViewModel(private val repository: HotelRepository) : ViewModel(), Observable {

    val employee = repository.employees
    private var isUpdateOrDelete = false
    private lateinit var employeeToUpdateOrDelete: Employee


    @Bindable
    val fullnameEmployee = MutableLiveData<String>()
    @Bindable
    val phoneEmployee = MutableLiveData<String>()
    @Bindable
    val salaryEmployee = MutableLiveData<String>()
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
            employeeToUpdateOrDelete.fullname = fullnameEmployee.value!!
            employeeToUpdateOrDelete.phone = phoneEmployee.value!!
            employeeToUpdateOrDelete.salary = salaryEmployee.value!!
            update(employeeToUpdateOrDelete)
        } else {
            val fullname = fullnameEmployee.value!!
            val phone = phoneEmployee.value!!
            val salary = salaryEmployee.value!!
            insert(Employee(0, fullname, phone, salary))
            fullnameEmployee.value = null
            phoneEmployee.value = null
            salaryEmployee.value = null
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete) {
            delete(employeeToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(employee: Employee) = viewModelScope.launch {
        val newRowId = repository.insert(employee)
        if(newRowId > -1) {
            statusMessage.value = Event("Hotel inserted successfully $newRowId")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun update(employee: Employee) = viewModelScope.launch {
        val noOfRows = repository.update(employee)
        if (noOfRows > 0) {
            fullnameEmployee.value = null
            phoneEmployee.value = null
            salaryEmployee.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Employee updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(employee: Employee) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(employee)
        if (noOfRowsDeleted > 0) {
            fullnameEmployee.value = null
            phoneEmployee.value = null
            salaryEmployee.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Employee deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAllEmployee()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("All employees deleted successfully")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun initUpdateAndDelete(employee: Employee) {
        fullnameEmployee.value = employee.fullname
        phoneEmployee.value = employee.phone
        salaryEmployee.value = employee.salary
        isUpdateOrDelete = true
        employeeToUpdateOrDelete = employee
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}