package com.example.samgim

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.hide() // 액션바 숨김(안숨겨짐)

    }
}


//        // 시작하기 버튼 클릭 리스너 설정
//        welcomeBtn.setOnClickListener {
//            // 다음 화면으로 이동하는 Intent 생성
//            val intent = Intent(this, NextActivity::class.java)
//            startActivity(intent) // 다음 화면으로 이동
//        }
//    }
//    }