package com.example.samgim.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.samgim.R
import com.example.samgim.databinding.HistoryDetailBinding
import com.example.samgim.ui.DB.TodolistDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class DetailFragment : Fragment() {

    private var _binding: HistoryDetailBinding? = null
    private val binding get() = _binding!!
    private var todoDB : TodolistDB? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoId = arguments?.getLong("todoId") ?: -1L // 기본값은 잘못된 ID를 의미하도록 설정

        // TodolistDB 초기화
        todoDB = TodolistDB.getInstance(requireContext())

        // todoId를 가져옴
        if (todoId != -1L) {
            // 잘못된 값이 아닐 경우 코루틴을 통해서 데이터베이스 작업을 비동기로 처리
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

        binding.confirmBtn.setOnClickListener {
            requireActivity().finish()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}