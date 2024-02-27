package com.example.samgim.ui.welcome

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.samgim.MainActivity
import com.example.samgim.R

class WelcomeActivity : AppCompatActivity() {
        // 시작하기 버튼 클릭 리스너 설정
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_welcome)

            val welcomeBtn = findViewById<Button>(R.id.welcomeBtn)

            welcomeBtn.setOnClickListener {
                val pref = this.getSharedPreferences("access", Activity.MODE_PRIVATE)

                val isFirst = pref.getBoolean("isFirst", true)
                if(isFirst){
                    val intent = Intent(this, TutorialActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }


