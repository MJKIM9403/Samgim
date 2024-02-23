package com.example.samgim.ui.character

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.samgim.R
import com.example.samgim.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor
    private lateinit var levelRequiredExp: List<Int>
    private var totalExp: Int = 0
    private var level: Int = 0
    private var maxLevelCount: Int = 0
    private var nextLevelRequiredExp: Int = 0
    private var currentLevelRequiredExp: Int = 0
    private var currentExpToShow: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeVariables()
        setupCharacterImage()
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun initializeVariables() {
        pref = requireActivity().getSharedPreferences("state", Activity.MODE_PRIVATE)
        prefEditor = pref.edit()

        levelRequiredExp = listOf(0, 10, 20, 40, 60, 80)
        totalExp = pref.getInt("totalExp", 0)
        level = checkLevel()
        maxLevelCount = levelRequiredExp.size
        nextLevelRequiredExp = when {
            level < maxLevelCount -> levelRequiredExp[level]
            else -> levelRequiredExp.last()
        }
        currentLevelRequiredExp = accumulateExp(level)
        currentExpToShow = totalExp - currentLevelRequiredExp
    }

    private fun setupCharacterImage() {
        val characterImg: ImageView = binding.characterImg
        val characterImages = arrayOf(
            R.drawable.rice01,
            R.drawable.rice02,
            R.drawable.rice03,
            R.drawable.rice04,
            R.drawable.rice05_1,
            R.drawable.rice05_3
        )
        characterImg.setImageResource(characterImages[level-1])
    }

    private fun updateUI() {
        val name: EditText = binding.characterName
        val editBtn: ImageButton = binding.editBtn
        val todayExp: TextView = binding.textViewTodayExpVal
        val progressBarExp: ProgressBar = binding.progressBarExp
        val download: TextView = binding.textViewDownload

        name.setText(pref.getString("name","김밥이"))
        name.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),android.R.color.transparent)

        editBtn.setOnClickListener {
            if(name.isEnabled){
                val inputName = name.text.toString()
                if(inputName.length > 10){
                    Toast.makeText(requireActivity(),"이름을 10자 이내로 설정하여 주십시오.",Toast.LENGTH_SHORT).show()
                }else if(inputName.trim().isEmpty()){
                    Toast.makeText(requireActivity(),"이름은 비워둘 수 없습니다.",Toast.LENGTH_SHORT).show()
                }else {
                    prefEditor.putString("name", name.text.toString())
                    prefEditor.apply()
                    Log.d("name",name.text.toString())
                    name.isEnabled = false
                    Toast.makeText(requireActivity(),"이름이 변경되었습니다.",Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }else {
                name.isEnabled = true
                name.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),android.R.color.darker_gray)
            }
        }

        val todayExpVal: Int = 10 // TODO: 오늘 작성한 리스트 중 완료된 값의 점수만 합산
        todayExp.text = "$todayExpVal 점"
        if (totalExp < accumulateExp(maxLevelCount)) {
            download.text = "레벨업까지 ${nextLevelRequiredExp - currentExpToShow}점!"
            progressBarExp.max = nextLevelRequiredExp
            progressBarExp.progress = currentExpToShow
        } else {
            download.text = "최대 레벨입니다."
            progressBarExp.max = levelRequiredExp[maxLevelCount - 1]
            progressBarExp.progress = levelRequiredExp[maxLevelCount - 1]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun accumulateExp(level: Int): Int {
        var accumulatedExp = 0
        for (i in 1..level) {
            accumulatedExp += levelRequiredExp[i - 1]
        }
        return accumulatedExp
    }

    private fun checkLevel(): Int {
        val prevLevel = pref.getInt("level", 1)
        val nowLevel: Int = when {
            totalExp in accumulateExp(1) until accumulateExp(2) -> 1
            totalExp in accumulateExp(2) until accumulateExp(3) -> 2
            totalExp in accumulateExp(3) until accumulateExp(4) -> 3
            totalExp in accumulateExp(4) until accumulateExp(5) -> 4
            totalExp in accumulateExp(5) until accumulateExp(6) -> 5
            totalExp >= accumulateExp(6) -> 6
            else -> 0
        }

        if (prevLevel < nowLevel) {
            prefEditor.putInt("level", nowLevel)
            prefEditor.apply()
        }

        return pref.getInt("level", 1)
    }
}