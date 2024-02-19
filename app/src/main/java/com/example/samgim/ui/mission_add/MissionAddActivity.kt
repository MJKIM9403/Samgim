package com.example.samgim.ui.mission_add

import HintArrayAdapter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.samgim.R


class MissionAddActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mission_add)

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

        // 선택된 값 표시하기
//        val result = findViewById<TextView>(R.id.resultText)

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                // 선택된 경우
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                result.setText(spinner.selectedItem.toString())
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // 아무것도 선택되지 않은 경우
//            }
//
//        }
    }
}