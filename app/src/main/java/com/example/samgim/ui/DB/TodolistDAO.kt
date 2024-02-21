package com.example.samgim.ui.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodolistDAO {
    // 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodos(vararg todolist: Todolist)

    @Insert
    fun insertTodo(todolist: Todolist)

    // 모든 투두 조회
    @Query("SELECT * FROM todolist")
    fun getAll(): List<Todolist>

    // 갱신
    @Update
    fun updateTodos(vararg todolist: Todolist)

    // 아이디로 특정 투두 삭제
//    @Query("DELETE FROM todolist WHERE list_id =:")
//    fun deleteTodos(vararg todolist: Todolist)
}