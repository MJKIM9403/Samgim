package com.example.samgim.ui.importance

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.samgim.MainActivity
import com.example.samgim.R
import com.example.samgim.data.Points
import com.example.samgim.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {
    var todolistSize: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            val intent: Intent = intent
            todolistSize = intent.getIntExtra("todolistSize", 0)

            val transaction = supportFragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putInt("todolistSize", todolistSize)
            val settingsFragment = SettingsFragment()
            settingsFragment.arguments = bundle

            transaction.replace(R.id.settings, settingsFragment).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveBtn.setOnClickListener {
            saveSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        val settingMsg = findViewById<TextView>(R.id.setting_msg)
        when {
            todolistSize == 0 -> {
                settingMsg.text = resources.getString(R.string.importance_setting_msg)
            }
            todolistSize > 0 -> {
                settingMsg.text = resources.getString(R.string.importance_setting_disable_msg)
            }
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

    private fun saveSettings() {
        val points = Points(this)
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
class SettingsFragment : PreferenceFragmentCompat() {

    var settingEnable: SwitchPreference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.importance_setting, rootKey)

        val todolistSize = this.arguments?.getInt("todolistSize")

        if(rootKey == null){
            Log.d("test",todolistSize.toString())
            settingEnable = findPreference("setting_enable")
            if(settingEnable != null && todolistSize != null){
                when {
                    todolistSize == 0 -> {
                        Log.d("test","size: 0")
                        settingEnable?.isChecked = true
                    }
                    todolistSize > 0 -> {
                        Log.d("test","size: 1이상")
                        settingEnable?.isChecked = false
                    }
                }
            }
        }
    }
}