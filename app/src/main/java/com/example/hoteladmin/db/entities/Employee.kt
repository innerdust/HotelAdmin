package com.example.hoteladmin.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int,
        @ColumnInfo(name = "fullname")
        var fullname: String,
        @ColumnInfo(name = "phone")
        var phone: String,
        @ColumnInfo(name = "salary")
        var salary: String
)