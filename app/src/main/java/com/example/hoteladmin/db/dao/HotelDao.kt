package com.example.hoteladmin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hoteladmin.db.entities.*

@Dao
interface HotelDao {
    @Insert
    suspend fun insertHotel(hotel: Hotel) : Long

    @Update
    suspend fun updateHotel(hotel: Hotel) : Int

    @Delete
    suspend fun deleteHotel(hotel: Hotel) : Int

    @Query("DELETE FROM hotel")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM hotel")
    fun getAllHotels() : LiveData<List<Hotel>>

    @Insert
    suspend fun insertPassport(passport: Passport) : Long

    @Update
    suspend fun updatePassport(passport: Passport) : Int

    @Delete
    suspend fun deletePassport(passport: Passport) : Int

    @Query("DELETE FROM passport")
    suspend fun deleteAllPassport() : Int

    @Query("SELECT * FROM passport")
    fun getAllPassports() : LiveData<List<Passport>>

    @Insert
    suspend fun insertManager(manager: Manager) : Long

    @Update
    suspend fun updateManager(manager: Manager) : Int

    @Delete
    suspend fun deleteManager(manager: Manager) : Int

    @Query("DELETE FROM manager")
    suspend fun deleteAllManager() : Int

    @Query("SELECT * FROM manager")
    fun getAllManager() : LiveData<List<Manager>>

    @Insert
    suspend fun insertCustomer(customer: Customer) : Long

    @Update
    suspend fun updateCustomer(customer: Customer) : Int

    @Delete
    suspend fun deleteCustomer(customer: Customer) : Int

    @Query("DELETE FROM customer")
    suspend fun deleteAllCustomer() : Int

    @Query("SELECT * FROM customer")
    fun getAllCustomer() : LiveData<List<Customer>>

    @Insert
    suspend fun insertEmployee(employee: Employee) : Long

    @Update
    suspend fun updateEmployee(employee: Employee) : Int

    @Delete
    suspend fun deleteEmployee(employee: Employee) : Int

    @Query("DELETE FROM employee")
    suspend fun deleteAllEmployee() : Int

    @Query("SELECT * FROM employee")
    fun getAllEmployee() : LiveData<List<Employee>>

    @Insert
    suspend fun insertService(service: Service) : Long

    @Update
    suspend fun updateService(service: Service) : Int

    @Delete
    suspend fun deleteService(service: Service) : Int

    @Query("DELETE FROM service")
    suspend fun deleteAllService() : Int

    @Query("SELECT * FROM service")
    fun getAllService() : LiveData<List<Service>>
}