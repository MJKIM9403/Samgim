package com.example.samgim.ui.mission_detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.samgim.MainActivity
import com.example.samgim.R
import com.example.samgim.databinding.MissionDetailBinding
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.mission_edit.MissionEditActivity
import com.example.samgim.ui.todolist.TodolistFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class MissionDetailActivity : AppCompatActivity() {
    private lateinit var binding: MissionDetailBinding
    private var todoDB : TodolistDB? = null
    private var todoId : Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MissionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                        // 상세보기 페이지이므로 Spinner를 비활성화
                        binding.spinner.isEnabled = false
                        if(it.todo_check) {
                            // 이미 경험치를 올린 미션일 경우 삭제버튼을 비활성화
                            binding.deleteBtn.visibility = View.GONE
                        }
                        // 화면에 저장된 데이터를 표시
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


        // 삭제 버튼 클릭 시
        binding.deleteBtn.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        // 수정 버튼 클릭 시
        binding.editBtn.setOnClickListener {
            val intent = Intent(this, MissionEditActivity::class.java).apply {
                putExtra("todoId", todoId)
                // todoId 잘 보내지는지 확인
                Log.d("test",todoId.toString())
            }
            startActivity(intent)
        }
    }


    // 카테고리 찾는 함수
    private fun findCategoryIndex(category: String): Int {
        val categories = resources.getStringArray(R.array.todo_array)
        return categories.indexOf(category)
    }

    // 뒤로가기 눌렀을 때 함수
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return super.onBackPressed()
    }

    // 액션 바 뒤로가기 아이콘 클릭 시 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // 삭제 로직
    fun showDeleteConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("오늘의 미션을 정말로 삭제하시겠습니까?")
            .setMessage("미션을 지우면 복구가 불가능합니다.")
            .setPositiveButton("예") { dialog, which ->
                // "예" 버튼을 눌렀을 때의 동작 구현
                CoroutineScope(Dispatchers.IO).launch {
                    // todoId를 사용하여 listId 초기화
                    val listId = todoId
                    // 데이터베이스에서 항목 삭제
                    withContext(Dispatchers.IO) {
                        todoDB?.getDAO()?.deleteTodos(listId)
                    }
                    // DB 갱신
                    todoDB?.getDAO()?.getAll()
                    // 토스트 메시지를 표시
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MissionDetailActivity, "삭제 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                finish()
            }
            .setNegativeButton("아니오", null)
            .create()

        dialogBuilder.show()
    }
}