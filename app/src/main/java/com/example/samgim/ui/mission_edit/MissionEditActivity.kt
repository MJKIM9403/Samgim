package com.example.samgim.ui.mission_edit


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.samgim.R
import com.example.samgim.databinding.MissionEditBinding
import com.example.samgim.ui.DB.TodolistDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class MissionEditActivity : AppCompatActivity() {
    private lateinit var binding: MissionEditBinding
    private var todoDB : TodolistDB? = null
    private var todoId : Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MissionEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TodoListDB 인스턴스를 초기화합니다.
        todoDB = TodolistDB.getInstance(applicationContext)

        // todoId 가져오기
        todoId = intent.getLongExtra("todoId", -1L) // 해당하는 값이 없으면 -1
        Log.d("test",todoId.toString())


        if (todoId != -1L) {
            // 잘못된 값이 아닐 경우 코루틴을 통해서 데이터베이스 작업을 비동기로 처리
            CoroutineScope(Dispatchers.IO).launch {
                val todolist = todoDB?.getDAO()?.getTodoById(todoId)
                Log.d("test3", todolist.toString())
                withContext(Dispatchers.Main) {
                    todolist?.let {
                        binding.todoId.text = it.listId.toString()
                        binding.spinner.setSelection(findCategoryIndex(it.category))
                        binding.writeTitle.setText(it.title)
                        binding.writeMemo.setText(it.contents)
                        // Date 타입의 regdate를 문자열로 포매팅
                        binding.todoRegdate.text =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it.regdate)
                    }
                }
            }
        }


        binding.cancelBtn.setOnClickListener {
            this.finish()
        }

        binding.editBtn.setOnClickListener {
            showConfirmationDialog()
        }

    }



    // 다이얼로그 표시 및 처리 함수 정의
    private fun showConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("오늘의 미션을 수정하시겠습니까?")
            .setMessage("수정 후에는 이전 데이터가 사라집니다.")
            .setPositiveButton("예") { dialog, which ->
                // "예" 버튼을 눌렀을 때의 동작 구현
                updateMission() // 미션 업데이트 함수 호출


            }
            .setNegativeButton("아니오", null)
            .create()

        alertDialog.show()
    }

    // 미션 업데이트 함수 정의
    private fun updateMission() {
        // 사용자가 입력한 새로운 제목, 내용, 카테고리 가져오기
        val newTitle = binding.writeTitle.text.toString()
        val newContents = binding.writeMemo.text.toString()
        val newCategory = binding.spinner.selectedItem.toString()


        if (todoId != -1L) {
            // 기존의 미션을 데이터베이스에서 가져와 업데이트
            CoroutineScope(Dispatchers.IO).launch {
                val todo = todoDB?.getDAO()?.getTodoById(todoId)
                todo?.let {
                    // 새로운 값으로 미션 업데이트
                    it.title = newTitle
                    it.contents = newContents
                    it.category = newCategory

                    // 데이터베이스에 업데이트된 미션 저장
                    todoDB?.getDAO()?.updateTodos(it)

                    // UI 스레드에서 토스트 메시지를 표시합니다.
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MissionEditActivity, "수정 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
    // 카테고리 찾는 함수
    private fun findCategoryIndex(category: String): Int {
        val categories = resources.getStringArray(R.array.todo_array)
        return categories.indexOf(category)
    }

}

