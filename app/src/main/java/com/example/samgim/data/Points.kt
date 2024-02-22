package com.example.samgim.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

data class Points(val context: Context){
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val mealPoint = sharedPreferences.getInt("mealPoint",1)
    val studyPoint = sharedPreferences.getInt("studyPoint",1)
    val workoutPoint = sharedPreferences.getInt("workoutPoint",1)
    val sleepPoint = sharedPreferences.getInt("sleepPoint",1)
    val etcPoint = sharedPreferences.getInt("etcPoint",1)
}
