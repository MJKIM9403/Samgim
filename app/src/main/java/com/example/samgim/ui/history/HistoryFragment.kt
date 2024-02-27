package com.example.samgim.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.databinding.FragmentHistoryBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.detail.DetailActivity
import com.example.samgim.ui.history.HistoryAdapter
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var todoDB: TodolistDB? = null
    private var historyList = listOf<Todolist>()
    private lateinit var hAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoDB = TodolistDB.getInstance(requireActivity())
        hAdapter = HistoryAdapter(requireActivity(), historyList, object : HistoryAdapter.OnItemClickListener {
            override fun onItemClick(todolist: Todolist) {
                val intent = Intent(activity, DetailActivity::class.java).apply {
                    putExtra("todoId", todolist.listId)
                    // todoId를 DetailActivity로 넘김
                }
                startActivity(intent)
            }
        })

        fetchHistory()

        binding.recyclerView2.adapter = hAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
        binding.recyclerView2.setHasFixedSize(true)
    }

    private fun fetchHistory() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = format.format(Date())
        val today: Date = format.parse(todayStr) ?: Date()

        val r = Runnable {
            try {
                historyList = todoDB?.getDAO()?.getAll()?.filter {
                    it.regdate.before(today)
                } ?: listOf()

                activity?.runOnUiThread {
                    hAdapter.updateData(historyList)
                    hAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.d("HistoryFragment", "Error - $e")
            }
        }

        Thread(r).start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
