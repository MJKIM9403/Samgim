package com.example.samgim.ui.detail
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.samgim.R
import com.example.samgim.ui.history.DetailFragment

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_host)

        if (savedInstanceState == null) {
            val fragment = DetailFragment().apply {
                arguments = intent.extras
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }

        // 액션 바 왼쪽에 뒤로가기 아이콘을 만들기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 뒤로가기 눌렀을 때 함수
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // 액션 바 뒤로가기 아이콘 클릭 시 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}