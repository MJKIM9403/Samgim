package com.example.samgim.ui.detail
import android.os.Bundle
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
    }
}