package com.example.samgim.ui.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface TodolistDAO {
    // 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodos(vararg todolist: Todolist)

    @Insert
    fun insertTodo(todolist: Todolist)

    // 모든 투두 조회
    @Query("SELECT * FROM todolist ORDER BY regdate DESC, listId ASC")
    fun getAll(): List<Todolist>

    // 날짜별 투두 조회(오늘의 미션 페이지 or 히스토리에서 특정 날짜 검색(추가 할거면))
    @Query("SELECT * FROM todolist WHERE regdate = :regdate ORDER BY listId ASC")
    fun getAllByRegdate(regdate: Date): List<Todolist>

    // 갱신
    @Update
    fun updateTodos(vararg todolist: Todolist)

    // 아이디로 특정 투두 삭제
    @Query("DELETE FROM todolist WHERE listId = :listId")
    fun deleteTodos(listId: Long)
}