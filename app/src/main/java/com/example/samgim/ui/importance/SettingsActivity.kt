package com.example.samgim.ui.importance

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.samgim.MainActivity
import com.example.samgim.R
import com.example.samgim.data.Points
import com.example.samgim.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveBtn.setOnClickListener {
            saveSettings()
        }
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.importance_setting, rootKey)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveSettings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === android.R.id.home){
            saveSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val points = Points(sharedPreferences)
        val mealPoint = points.mealPoint
        val studyPoint = points.studyPoint
        val workoutPoint = points.workoutPoint
        val sleepPoint = points.sleepPoint
        val etcPoint = points.etcPoint
        val total = mealPoint + studyPoint + workoutPoint + sleepPoint + etcPoint

        if(total != 10){
            Toast.makeText(this, "점수의 총합을 10점으로 맞춰주세요.", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}