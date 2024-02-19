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

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.importance_setting, rootKey)
        }
    }

    override fun onBackPressed() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mealPoint = sharedPreferences.getInt("mealPoint",1)
        val studyPoint = sharedPreferences.getInt("studyPoint",1)
        val workoutPoint = sharedPreferences.getInt("workoutPoint",1)
        val sleepPoint = sharedPreferences.getInt("sleepPoint",1)
        val etcPoint = sharedPreferences.getInt("etcPoint",1)
        val total = mealPoint + studyPoint + workoutPoint + sleepPoint + etcPoint

        if(total != 10){
            Toast.makeText(this, "점수의 총합을 10점으로 맞춰주세요.", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}