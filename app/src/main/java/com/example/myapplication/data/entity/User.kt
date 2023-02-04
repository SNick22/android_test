package com.example.myapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "user")
data class User(
    val name: String,
    @PrimaryKey
    var email: String,
    val password: String,
    @TypeConverters(ConvertBool::class)
    var checkbox1: Boolean = true,
    @TypeConverters(ConvertBool::class)
    var checkbox2: Boolean = true,
    @TypeConverters(ConvertBool::class)
    var checkbox3: Boolean = true,
)
