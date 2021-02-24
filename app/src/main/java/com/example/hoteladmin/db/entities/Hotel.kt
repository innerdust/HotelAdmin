package com.example.hoteladmin.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel")
data class Hotel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "address")
    var address: String
)