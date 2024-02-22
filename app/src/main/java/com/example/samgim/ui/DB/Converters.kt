package com.example.samgim.ui.DB

import android.annotation.SuppressLint
import android.util.Log
import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class Converters {
    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun fromTimestamp(strDate: String?): Date? {
        var date: Date? = null
        try {
            val format = SimpleDateFormat("yyyy-MM-dd")
            date = strDate?.let { format.parse(it) }
        }catch (e: ParseException){
            Log.d("TypeConverter","Parse Exception")
        }
        return date
    }

    @SuppressLint("SimpleDateFormat")
    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val strDate = date?.let { format.format(it) }
        return strDate
    }

}