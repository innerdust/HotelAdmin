package com.example.hoteladmin.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passport")
data class Passport (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int,
        @ColumnInfo(name = "series")
        var series: String,
        @ColumnInfo(name = "number")
        var number: String,
        @ColumnInfo(name = "date_of_birth")
        var date_of_birth: String,
        @ColumnInfo(name = "registered")
        var registered: String
)