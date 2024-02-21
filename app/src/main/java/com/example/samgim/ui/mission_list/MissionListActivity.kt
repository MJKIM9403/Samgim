package com.example.samgim.ui.mission_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.databinding.MissionListBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.DB.TodolistDB.Companion.destroyInstance
import com.example.samgim.ui.mission_add.MissionAddActivity

class MissionListActivity : AppCompatActivity() {
    private lateinit var binding: MissionListBinding
    private var todoDB : TodolistDB? = null
    private var todoList = listOf<Todolist>()
    lateinit var tAdapter : TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MissionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoDB = TodolistDB.getInstance(this)
        tAdapter = TodoAdapter(this, todoList)

        val r = Runnable {
            try {
                todoList = todoDB?.getDAO()?.getAll()!!
                tAdapter = TodoAdapter(this, todoList)
                tAdapter.notifyDataSetChanged()

                binding.recyclerView.adapter = tAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        binding.mAddBtn.setOnClickListener {
            val i = Intent(this, MissionAddActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        TodolistDB.destroyInstance()
        todoDB = null
        super.onDestroy()
    }
}