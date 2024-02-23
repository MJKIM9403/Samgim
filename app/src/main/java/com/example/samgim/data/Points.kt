package com.example.samgim.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

data class Points(val context: Context){
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val mealPoint: Int = sharedPreferences.getInt("mealPoint",1)
    val studyPoint: Int = sharedPreferences.getInt("studyPoint",1)
    val workoutPoint: Int = sharedPreferences.getInt("workoutPoint",1)
    val sleepPoint: Int = sharedPreferences.getInt("sleepPoint",1)
    val etcPoint: Int = sharedPreferences.getInt("etcPoint",1)

    fun getPoint(category: String): Int {
        return when(category){
            "식사" -> mealPoint
            "공부" -> studyPoint
            "운동" -> workoutPoint
            "수면" -> sleepPoint
            "기타" -> etcPoint
            else -> 0
        }
    }
}
