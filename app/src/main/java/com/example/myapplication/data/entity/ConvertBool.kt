package com.example.myapplication.data.entity

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class ConvertBool {

    @TypeConverter
    fun boolToInt(bool: Boolean) = if (bool) 1 else 0

    @TypeConverter
    fun intToBool(int: Int) = int == 1
}