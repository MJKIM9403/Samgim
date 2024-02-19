package com.example.samgim.ui.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.samgim.R

class WelcomeActivity : AppCompatActivity() {

        // 시작하기 버튼 클릭 리스너 설정
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_welcome)

            val welcomeBtn = findViewById<Button>(R.id.welcomeBtn)


//            welcomeBtn.setOnClickListener {
//                val intent = Intent(this, TutorialActivity::class.java)
//                startActivity(intent)
//
//                val decorView: View = window.decorView
//                var uiOption: Int = decorView.systemUiVisibility
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//                    uiOption = uiOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                    uiOption = uiOption or View.SYSTEM_UI_FLAG_FULLSCREEN
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//                    uiOption = uiOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//
//                decorView.systemUiVisibility = uiOption
//            }
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