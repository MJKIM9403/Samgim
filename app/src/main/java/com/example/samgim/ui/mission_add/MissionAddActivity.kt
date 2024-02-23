package com.example.samgim.ui.mission_add

import HintArrayAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.samgim.MainActivity
import com.example.samgim.R
import com.example.samgim.databinding.MissionAddBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.DB.TodolistDB.Companion.destroyInstance
import com.example.samgim.ui.mission_list.MissionListActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


class MissionAddActivity : AppCompatActivity() {
    private lateinit var binding: MissionAddBinding
    private var todoDB : TodolistDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MissionAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 항목을 포함한 배열 가져오기
        val categories = resources.getStringArray(R.array.todo_array).toMutableList()

        // Hint를 추가하기 위해 배열의 첫 번째 위치에 빈 문자열 추가
        categories.add(0, "")

        // 커스텀 어댑터를 생성할 때 Activity의 context를 전달
        val adapter = HintArrayAdapter(this, categories)

        // Spinner에 어댑터 설정
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        // 초기 선택 항목 설정 (여기서는 hint를 선택하지 않도록 설정)
        spinner.setSelection(0, false)

        // 일정 추가 버튼
        val addBtn = binding.addBtn
        // 일정 취소 버튼
        val cancelBtn = binding.cancelBtn

        // 선택된 값 표시하기 테스트
        val result = binding.selectCategory

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                // 선택된 경우
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result.setText(spinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.setText("") // 선택 안하면 카테고리 값은 안들어감
            }

        }

        todoDB = TodolistDB.getInstance(this)

        // 새로운 투두 객체를 생성, id 이외의 값을 지정 후 DB에 추가
        val addRunnable = Runnable {
            val newTodo = Todolist(
                title = binding.writeTitle.text.toString(), // 제목
                contents = binding.writeMemo.text.toString(), // 내용
                category = binding.selectCategory.text.toString(), // 카테고리
            )
            todoDB?.getDAO()?.insertTodo(newTodo)
        }

        // 등록 버튼 클릭 리스너 설정
        addBtn.setOnClickListener {
            // 다이얼로그 표시
            val builder = AlertDialog.Builder(this)
            builder.setTitle("등록 확인")
            builder.setMessage("등록하시겠습니까?")

            // "예" 버튼 클릭 시 이벤트 처리
            builder.setPositiveButton("예") { dialog, which ->
                // 여기에 등록 로직을 추가할 수 있습니다.

                val addThread = Thread(addRunnable)
                addThread.start()

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()

                Toast.makeText(this, "등록되었습니다.", Toast.LENGTH_SHORT).show()
            }

            // "아니오" 버튼 클릭 시 이벤트 처리
            builder.setNegativeButton("아니오") { dialog, which ->
                // 아무 동작 없음
            }

            // 다이얼로그 표시
            builder.show()


        }

        // 취소 버튼 클릭 리스너 설정
        cancelBtn.setOnClickListener {
            showCancelBtnDialog()
        }

    }

    override fun onDestroy() {
        TodolistDB.destroyInstance()
        super.onDestroy()
    }



    // 등록 다이얼로그 표시 함수
    private fun showCancelBtnDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("등록 취소 확인")
        builder.setMessage("등록을 취소하시겠습니까? 작성한 내용이 사라집니다.")

        // "예" 버튼 클릭 시 이벤트 처리
        builder.setPositiveButton("예") { dialog, which ->
            // 여기에 등록 로직을 추가할 수 있습니다.
            Toast.makeText(this, "등록이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            finish()

        }

        // "아니오" 버튼 클릭 시 이벤트 처리
        builder.setNegativeButton("아니오") { dialog, which ->
            // 아무 동작 없음
        }

        // 다이얼로그 표시
        builder.show()
    }


}