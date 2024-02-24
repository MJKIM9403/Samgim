package com.example.samgim.ui.mission_edit



import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.R
import com.example.samgim.databinding.FragmentEditBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.detail.DetailActivity
import com.example.samgim.ui.history.HistoryAdapter
import com.example.samgim.ui.mission_list.TodoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private var todoDB : TodolistDB? = null
    private var todoList = listOf<Todolist>()
    lateinit var eAdapter : EditAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoId = arguments?.getLong("todoId") ?: -1L // 기본값은 잘못된 ID를 의미하도록 설정

        // TodolistDB 초기화
        todoDB = TodolistDB.getInstance(requireActivity())
        eAdapter = EditAdapter(requireActivity(), todoList, object : EditAdapter.OnItemClickListener {
            override fun onItemClick(todolist: Todolist) {
                val intent = Intent(activity, MissionEditActivity::class.java).apply {
                    putExtra("todoId", todolist.listId)
                    // 필요한 다른 데이터를 putExtra로 추가
                }
                startActivity(intent)
            }
        })

        fetchHistory()

        binding.recyclerView3.adapter = eAdapter
        binding.recyclerView3.layoutManager = LinearLayoutManager(context)
        binding.recyclerView3.setHasFixedSize(true)
    }

    private fun fetchHistory() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = format.format(Date())
        val today: Date = format.parse(todayStr) ?: Date()

        val r = Runnable {
            try {
                todoList = todoDB?.getDAO()?.getAllDESC()?.filter {
                    it.regdate.before(today)
                } ?: listOf()

                activity?.runOnUiThread {
                    eAdapter.updateData(todoList)
                    eAdapter.notifyDataSetChanged()
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