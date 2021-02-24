package com.example.hoteladmin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hoteladmin.db.dao.HotelDao
import com.example.hoteladmin.db.entities.*

@Database(entities = arrayOf(
    Hotel::class,
    Booking::class,
    Employee::class,
    Customer::class,
    Manager::class,
    Passport::class,
    RoomCategory::class,
    RoomService::class,
    Service::class),
        version = 2)
abstract class HotelDatabase : RoomDatabase() {

    abstract val hotelDao: HotelDao

    companion object {
        @Volatile
        private var INSTANCE: HotelDatabase? = null
            fun getInstance(context: Context): HotelDatabase {
                synchronized(this) {
                    var instance = INSTANCE
                        if(instance == null) {
                            instance = Room.databaseBuilder(
                                context.applicationContext,
                                HotelDatabase::class.java,
                                "hotel_admin_db"
                            ).fallbackToDestructiveMigration().build()
                        }
                    return instance
                }
            }
    }


}