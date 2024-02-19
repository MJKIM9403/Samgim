package com.example.samgim.ui.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodolistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is todolist Fragment"
    }
    val text: LiveData<String> = _text
}