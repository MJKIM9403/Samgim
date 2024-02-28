package com.example.samgim

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.samgim.Util.DateFomatter
import com.example.samgim.data.Points
import com.example.samgim.databinding.ActivityMainBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.importance.SettingsActivity
import com.example.samgim.ui.welcome.TutorialActivity
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var todoDB: TodolistDB? = null
    private var todolistSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!Points(this).isCompleted()){
            goSetting()
        }

        val navView: BottomNavigationView = binding.navView

        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_todolist, R.id.navigation_character, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        // Room은 메인 스레드에서 접근하려고 하면 에러가 발생하므로 백그라운드에서 작업해야한다.
        todoDB = TodolistDB.getInstance(this)
        val format = android.icu.text.SimpleDateFormat("yyyy-MM-dd")
        val today: Date = format.parse(DateFomatter.dateFormat(Date()))

        val r = Runnable {
            todolistSize = todoDB?.getDAO()?.getCountByRegdate(today)!!
        }
        val thread = Thread(r)
        thread.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId===R.id.menu_main_setting){
            goSetting()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goSetting() {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("todolistSize", todolistSize)
        startActivity(intent)
    }

    /* 뒤로가기 두번 시 프로그램 종료 */
    private var pressedTime: Long = 0

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            //마지막으로 누른 '뒤로가기' 버튼 클릭 시간이 이전의 '뒤로가기' 버튼 클릭 시간과의 차이가 2초보다 크면
            if(System.currentTimeMillis() > pressedTime + 2000){
                //현재 시간을 pressedTime 에 저장
                pressedTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(),"뒤로가기를 한번 더 누르면 종료.", Toast.LENGTH_SHORT).show();
            }

            //마지막 '뒤로가기' 버튼 클릭시간이 이전의 '뒤로가기' 버튼 클릭 시간과의 차이가 2초보다 작으면
            else{
                ActivityCompat.finishAffinity(this@MainActivity);
                exitProcess(0);
            }
        }
    }
}