package com.example.samgim.ui.DB

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todolist::class], version = 1)
abstract class TodolistDB: RoomDatabase() {
    abstract fun getDAO() : TodolistDAO

    companion object {
        private var INSTANCE: TodolistDB? = null

        fun getInstance(context: Context): TodolistDB {
            if(INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TodolistDB::class.java,
                        "todo_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}