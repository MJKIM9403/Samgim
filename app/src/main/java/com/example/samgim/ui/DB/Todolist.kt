package com.example.samgim.ui.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Entity(tableName = "todolist")
data class Todolist(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "contents")
    var contents: String,
    @ColumnInfo(name = "category")
    var category: String,
) {
    @PrimaryKey(autoGenerate = true)
    var listId: Long = 0
    @ColumnInfo(name = "todo_check")
    var todo_check: Boolean = false
    @ColumnInfo(name = "regdate")
    var regdate: Date = Date()

    override fun toString(): String {
        return "title = $title, " +
                "contents = $contents, " +
                "category = $category, " +
                "regdate = $regdate, " +
                "todo_check = $todo_check"
    }
}