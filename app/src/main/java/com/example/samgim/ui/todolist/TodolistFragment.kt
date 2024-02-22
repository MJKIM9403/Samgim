package com.example.samgim.ui.todolist

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.Util.DateFomatter
import com.example.samgim.databinding.FragmentTodolistBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.mission_add.MissionAddActivity
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
        tAdapter = TodoAdapter(requireActivity(), todoList)

        val r = Runnable {
            try {
                todoList = todoDB?.getDAO()?.getAllByRegdate(today)!!
                tAdapter = TodoAdapter(requireActivity(), todoList)
                tAdapter.notifyDataSetChanged()

                binding.recyclerView.adapter = tAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        binding.mAddBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MissionAddActivity::class.java)
            startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}