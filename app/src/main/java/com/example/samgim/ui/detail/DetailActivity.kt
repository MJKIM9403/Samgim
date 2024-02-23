package com.example.samgim.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.samgim.R
import com.example.samgim.databinding.HistoryDetailBinding
import com.example.samgim.ui.DB.TodolistDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: HistoryDetailBinding
    private var todoDB : TodolistDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todoId = intent.getLongExtra("todoId", -1L) // 기본값은 잘못된 ID를 의미하도록 설정

        // TodolistDB 초기화
        todoDB = TodolistDB.getInstance(this)


        if (todoId != -1L) {
            CoroutineScope(Dispatchers.IO).launch {
                val todolist = todoDB?.getDAO()?.getTodoById(todoId)
                withContext(Dispatchers.Main) {
                    todolist?.let {
                        binding.todoId.text = it.listId.toString()
                        binding.todoCategory.text = it.category
                        binding.todoTitle.text = it.title
                        binding.todoMemo.text = it.contents
                        // Date 타입의 regdate를 문자열로 포매팅
                        binding.todoRegdate.text =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it.regdate)
                        // todo_check와 관련된 UI 업데이트 (예시)
                        if(todolist.todo_check) {
                            binding.todoCheck.setImageResource(R.drawable.baseline_thumb_up_24)
                        } else {
                            binding.todoCheck.setImageResource(R.drawable.baseline_thumb_down_24)
                        }

                    }
                }
            }
        }
    }
}