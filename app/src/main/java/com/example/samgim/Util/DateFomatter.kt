package com.example.samgim.Util

import java.text.SimpleDateFormat
import java.util.Date

class DateFomatter {
    companion object{
        fun dateFormat(regdate: Date): String{
            val format = SimpleDateFormat("yyyy-MM-dd")
            val strDate = regdate.let { format.format(it) }
            return strDate
        }
    }
}