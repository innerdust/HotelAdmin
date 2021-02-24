package com.example.hoteladmin.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_category")
data class RoomCategory (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id : Int,
        @ColumnInfo(name = "number_of_rooms")
        var number_of_rooms: Int,
        @ColumnInfo(name = "cost_per_day")
        var cost_per_day: Int,
        @ColumnInfo(name = "name")
        var name: String
)