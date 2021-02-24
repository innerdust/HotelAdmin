package com.example.hoteladmin.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int,
        @ColumnInfo(name = "fullname")
        var fullname: String,
        @ColumnInfo(name = "phone")
        var phone: String
)