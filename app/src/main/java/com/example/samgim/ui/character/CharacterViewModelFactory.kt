package com.example.samgim.ui.character

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharacterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CharacterViewModel::class.java)){
            return CharacterViewModel(context = context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}