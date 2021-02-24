package com.example.hoteladmin.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking")
data class Booking (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int,
        @ColumnInfo(name = "status")
        var status: String,
        @ColumnInfo(name = "check_in")
        var check_in: Int,
        @ColumnInfo(name = "check_out")
        var check_out: Int,
        @ColumnInfo(name = "customer_id")
        var customer_id: Int,
        @ColumnInfo(name = "room_id")
        var room_id: String
)