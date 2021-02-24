package com.example.hoteladmin.viewmodel.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteladmin.db.entities.Hotel
import com.example.hoteladmin.db.entities.Passport
import com.example.hoteladmin.db.repository.HotelRepository
import com.example.hoteladmin.event.Event
import kotlinx.coroutines.launch

class PassportViewModel(private val repository: HotelRepository) : ViewModel(), Observable {

    val passport = repository.passports
    private var isUpdateOrDelete = false
    private lateinit var passportToUpdateOrDelete: Passport


    @Bindable
    val seriesOfPassport = MutableLiveData<String>()
    @Bindable
    val manNumber = MutableLiveData<String>()
    @Bindable
    val dateOfBirth = MutableLiveData<String>()
    @Bindable
    val dateOfRegistered = MutableLiveData<String>()
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
            passportToUpdateOrDelete.series = seriesOfPassport.value!!
            passportToUpdateOrDelete.number = manNumber.value!!
            passportToUpdateOrDelete.date_of_birth = dateOfBirth.value!!
            passportToUpdateOrDelete.registered = dateOfRegistered.value!!
            update(passportToUpdateOrDelete)
        } else {
            val series = seriesOfPassport.value!!
            val number = manNumber.value!!
            val date_of_birth = dateOfBirth.value!!
            val registered = dateOfRegistered.value!!
            insert(Passport(0, series, number, date_of_birth, registered))
            seriesOfPassport.value = null
            manNumber.value = null
            dateOfBirth.value = null
            dateOfRegistered.value = null
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete) {
            delete(passportToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(passport: Passport) = viewModelScope.launch {
        val newRowId = repository.insert(passport)
        if(newRowId > -1) {
            statusMessage.value = Event("Passport inserted successfully $newRowId")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun update(passport: Passport) = viewModelScope.launch {
        val noOfRows = repository.update(passport)
        if (noOfRows > 0) {
            seriesOfPassport.value = null
            manNumber.value = null
            dateOfBirth.value = null
            dateOfRegistered.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Passport updated successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(passport: Passport) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(passport)
        if (noOfRowsDeleted > 0) {
            seriesOfPassport.value = null
            manNumber.value = null
            dateOfBirth.value = null
            dateOfRegistered.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Passport deleted successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAllPassport()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("All passports deleted successfully")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun initUpdateAndDelete(passport: Passport) {
        seriesOfPassport.value = passport.series
        manNumber.value = passport.number
        dateOfBirth.value = passport.date_of_birth
        dateOfRegistered.value = passport.registered
        isUpdateOrDelete = true
        passportToUpdateOrDelete = passport
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}