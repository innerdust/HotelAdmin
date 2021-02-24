package com.example.hoteladmin.db.repository

import com.example.hoteladmin.db.dao.HotelDao
import com.example.hoteladmin.db.entities.*

class HotelRepository(private val dao: HotelDao) {

    val hotels = dao.getAllHotels()
    val passports = dao.getAllPassports()
    val managers = dao.getAllManager()
    val customers = dao.getAllCustomer()
    val employees = dao.getAllEmployee()
    val services = dao.getAllService()


    suspend fun insert(hotel: Hotel) : Long {
        return dao.insertHotel(hotel)
    }

    suspend fun update(hotel: Hotel) : Int {
        return dao.updateHotel(hotel)
    }

    suspend fun delete(hotel: Hotel) : Int {
        return dao.deleteHotel(hotel)
    }

    suspend fun deleteAll() : Int {
        return dao.deleteAll()
    }

    suspend fun insert(passport: Passport) : Long {
        return dao.insertPassport(passport)
    }

    suspend fun update(passport: Passport) : Int {
        return dao.updatePassport(passport)
    }

    suspend fun delete(passport: Passport) : Int {
        return dao.deletePassport(passport)
    }

    suspend fun deleteAllPassport() : Int {
        return dao.deleteAllPassport()
    }

    suspend fun insert(manager: Manager) : Long {
        return dao.insertManager(manager)
    }

    suspend fun update(manager: Manager) : Int {
        return dao.updateManager(manager)
    }

    suspend fun delete(manager: Manager) : Int {
        return dao.deleteManager(manager)
    }

    suspend fun deleteAllManager() : Int {
        return dao.deleteAllManager()
    }

    suspend fun insert(customer: Customer) : Long {
        return dao.insertCustomer(customer)
    }

    suspend fun update(customer: Customer) : Int {
        return dao.updateCustomer(customer)
    }

    suspend fun delete(customer: Customer) : Int {
        return dao.deleteCustomer(customer)
    }

    suspend fun deleteAllCustomer() : Int {
        return dao.deleteAllCustomer()
    }

    suspend fun insert(employee: Employee) : Long {
        return dao.insertEmployee(employee)
    }

    suspend fun update(employee: Employee) : Int {
        return dao.updateEmployee(employee)
    }

    suspend fun delete(employee: Employee) : Int {
        return dao.deleteEmployee(employee)
    }

    suspend fun deleteAllEmployee() : Int {
        return dao.deleteAllEmployee()
    }

    suspend fun insert(service: Service) : Long {
        return dao.insertService(service)
    }

    suspend fun update(service: Service) : Int {
        return dao.updateService(service)
    }

    suspend fun delete(service: Service) : Int {
        return dao.deleteService(service)
    }

    suspend fun deleteAllService() : Int {
        return dao.deleteAllService()
    }
}