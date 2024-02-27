package com.example.samgim.ui.history

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.databinding.FragmentHistoryBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.detail.DetailActivity
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var todoDB: TodolistDB? = null
    private var historyList = listOf<Todolist>()
    private lateinit var hAdapter: HistoryAdapter

    private var historyCount: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val picker : DatePicker = binding.datePicker1 //DatePicker id
        val showBtn : Button = binding.button1 //Button id
        val historyLayout : LinearLayout = binding.historyLayout

        val calendar: Calendar = Calendar.getInstance()
        val minDate = calendar
        val maxDate = calendar

        minDate.set(2020,1-1,1) // 보여줄 최소 날짜

        picker.minDate = minDate.time.time // 보여줄 최소 날짜 picker에 Set

        maxDate.set(2026,1-1,1) // 보여줄 최대 날짜
        picker.maxDate = maxDate.timeInMillis  // 보여줄 최대 날짜  picker에 적용 Set

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            picker.setOnDateChangedListener { _, year, month, dayOfMonth
                -> historyLayout.visibility = View.GONE
                val selectDate = "${year}-${month +1}-${dayOfMonth}"
                fetchHistory(selectDate)
            }
        }

        showBtn.setOnClickListener { // 버튼 click 시 선택 된 날짜에 등록된 미션 정보 불러옴
            if(historyCount > 0){
                historyLayout.visibility = View.VISIBLE
            }else if(historyCount == 0) {
                Toast.makeText(requireActivity(), "선택된 날짜에 기록된 미션이 없습니다.", Toast.LENGTH_SHORT).show()
            }

        }

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


        binding.recyclerView2.adapter = hAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
        binding.recyclerView2.setHasFixedSize(true)
    }

//    private fun fetchHistory() {
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val todayStr = format.format(Date())
//        val today: Date = format.parse(todayStr) ?: Date()
//
//        val r = Runnable {
//            try {
//                historyList = todoDB?.getDAO()?.getAll()?.filter {
//                    it.regdate.before(today)
//                } ?: listOf()
//
//                activity?.runOnUiThread {
//                    hAdapter.updateData(historyList)
//                    hAdapter.notifyDataSetChanged()
//                }
//            } catch (e: Exception) {
//                Log.d("HistoryFragment", "Error - $e")
//            }
//        }
//
//        Thread(r).start()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showHistoryCountMsg(): String {
        historyCount = historyList.size
        return when(historyCount) {
            0 -> "기록된 미션이 없습니다."
            else -> "${historyCount}개의 미션이 기록되어 있습니다."
        }
    }
    private fun fetchHistory(date: String) {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val findDate: Date = format.parse(date) ?: Date()
        val countShowView: TextView = binding.textView1

        val r = Runnable {
            try {
                historyList = todoDB?.getDAO()?.getAllByRegdate(findDate)!!

                // 결과가 나온 후에 showHistoryCountMsg 함수와 refreshHistory 함수를 동작하도록 Handler 설정
                Handler(Looper.getMainLooper()).post {
                    countShowView.text = showHistoryCountMsg()
                    refreshHistory()
                }
            } catch (e: Exception) {
                Log.d("HistoryFragment", "Error - $e")
            }
        }

        Thread(r).start()
    }

    private fun refreshHistory() {
        activity?.runOnUiThread {
            hAdapter.updateData(historyList)
            hAdapter.notifyDataSetChanged()
        }
    }
}
