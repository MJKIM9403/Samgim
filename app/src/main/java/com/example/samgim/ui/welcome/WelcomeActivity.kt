package com.example.samgim.ui.welcome

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.samgim.MainActivity
import com.example.samgim.R

class WelcomeActivity : AppCompatActivity() {
        // 시작하기 버튼 클릭 리스너 설정
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_welcome)

            val welcomeBtn = findViewById<Button>(R.id.welcomeBtn)

            welcomeBtn.setOnClickListener {
                val pref = this.getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
                val isFirst = pref.getBoolean("isFirst", true)
                if(isFirst){
                    val prefEditor: SharedPreferences.Editor = pref.edit()
                    prefEditor.putBoolean("isFirst", false)
                    prefEditor.apply()
                    val intent = Intent(this, TutorialActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
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