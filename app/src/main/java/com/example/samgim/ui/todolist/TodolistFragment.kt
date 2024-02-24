package com.example.samgim.ui.todolist

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.Util.DateFomatter
import com.example.samgim.databinding.FragmentTodolistBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.mission_add.MissionAddActivity
import com.example.samgim.ui.mission_edit.EditAdapter
import com.example.samgim.ui.mission_edit.MissionEditActivity
import com.example.samgim.ui.mission_list.TodoAdapter
import java.util.Date

class TodolistFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var todoDB : TodolistDB? = null
    private var todoList =  listOf<Todolist>()
    lateinit var tAdapter : TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today: Date = format.parse(DateFomatter.dateFormat(Date()))

        todoDB = TodolistDB.getInstance(requireActivity())
        tAdapter = TodoAdapter(requireActivity(), todoList, object : EditAdapter.OnItemClickListener {
            override fun onItemClick(todolist: Todolist) {
                val intent = Intent(activity, MissionEditActivity::class.java).apply {
                    putExtra("todoId", todolist.listId)
                    // 필요한 다른 데이터를 putExtra로 추가
                    Log.d("test",todolist.listId.toString())
                }
                startActivity(intent)
            }
        })

        Thread {
            todoList = todoDB?.getDAO()?.getAllByRegdate(today)!!
            setRecyclerView()
        }.start()

        binding.mAddBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MissionAddActivity::class.java)
            startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setRecyclerView() {
        activity?.runOnUiThread(
            Runnable {
                tAdapter.updateData(todoList)
                tAdapter.notifyDataSetChanged()

                binding.recyclerView.adapter = tAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.setHasFixedSize(true)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        refreshTodoList()
    }

    private fun refreshTodoList() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today: Date = format.parse(DateFomatter.dateFormat(Date()))

        Thread {
            todoList = todoDB?.getDAO()?.getAllByRegdate(today)!!
            // UI 업데이트는 메인 스레드에서 진행되어야 함
            activity?.runOnUiThread {
                tAdapter.updateData(todoList)
                tAdapter.notifyDataSetChanged()
            }
        }.start()
    }
}