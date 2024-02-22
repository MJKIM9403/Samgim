package com.example.samgim.ui.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar

@Entity(tableName = "todolist")
data class Todolist(
    @PrimaryKey(autoGenerate = true)
    val listId: Long = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "contents")
    var contents: String,
    @ColumnInfo(name = "category")
    var category: String,
//    @ColumnInfo(name = "regdate")
//    val regdate: SimpleDateFormat,
    @ColumnInfo(name = "todo_check")
    var todo_check: Boolean = false
) {

    override fun toString(): String {
        return "title = $title, " +
                "contents = $contents, " +
                "category = $category, " +
//                "regdate = $regdate, " +
                "todo_check = $todo_check"
    }
}