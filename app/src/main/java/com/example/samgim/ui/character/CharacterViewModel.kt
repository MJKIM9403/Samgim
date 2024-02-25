package com.example.samgim.ui.character

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samgim.R

class CharacterViewModel(context: Context) : ViewModel() {

    private val pref: SharedPreferences
    private val prefEditor: SharedPreferences.Editor
    private val levelRequiredExpList: List<Int> = listOf(0, 10, 20, 40, 60, 80)
    private val maxLevel: Int = levelRequiredExpList.size
    private val maxLevelAccumulatedExp: Int = accumulateExp(maxLevel)
    private val characterImages = arrayOf(
        R.drawable.rice01,
        R.drawable.rice02,
        R.drawable.rice03,
        R.drawable.rice04,
        R.drawable.rice05_1,
        R.drawable.rice05_3
    )

    private val _totalExp = MutableLiveData<Int>()
    private val _level = MutableLiveData<Int>()
    private val _nextLevelRequiredExp = MutableLiveData<Int>()
    private val _currentLevelAccumulatedExp = MutableLiveData<Int>()
    private val _currentExpToShow = MutableLiveData<Int>()
    private val _characterImage = MutableLiveData<Int>()
    private val _characterName = MutableLiveData<String>()

    val totalExp: LiveData<Int>  = _totalExp
    val level: LiveData<Int> = _level
    val nextLevelRequiredExp: LiveData<Int> = _nextLevelRequiredExp
    val currentLevelAccumulatedExp: LiveData<Int> = _currentLevelAccumulatedExp
    val currentExpToShow: LiveData<Int> = _currentExpToShow
    val characterImage: LiveData<Int> = _characterImage
    val characterName: LiveData<String> = _characterName

    init {
        pref = context.getSharedPreferences("state", Activity.MODE_PRIVATE)
        prefEditor = pref.edit()

        _totalExp.value = pref.getInt("totalExp", 0)
        _level.value = pref.getInt("level", 1)
        _nextLevelRequiredExp.value = levelRequiredExpList[_level.value!!]
        _currentLevelAccumulatedExp.value = accumulateExp(_level.value!!)
        _currentExpToShow.value = (totalExp.value)?.minus(_currentLevelAccumulatedExp.value!!)
        _characterImage.value = characterImages[_level.value!! - 1]
        _characterName.value = pref.getString("characterName", "김밥이")
    }

    fun updateState(point: Int){
        _totalExp.value = _totalExp.value?.plus(point)
        prefEditor.putInt("totalExp", _totalExp.value!!)
        prefEditor.apply()
        if(checkLevelUp()){
            _level.value = _level.value?.plus(1)
            _nextLevelRequiredExp.value = levelRequiredExpList[level.value!!]
            _currentLevelAccumulatedExp.value = accumulateExp(level.value!!)
            _characterImage.value = characterImages[level.value!! - 1]
        }
        _currentExpToShow.value = (totalExp.value)?.minus(currentLevelAccumulatedExp.value!!)
        Log.d("test","updateState")
    }

    fun updateCharacterName(name: String){
        prefEditor.putString("characterName", name)
        prefEditor.apply()

        _characterName.value = pref.getString("characterName", "김밥이")
    }


    private fun checkLevelUp(): Boolean {
        val prevLevel = pref.getInt("level", 1)
        val nowLevel: Int = when {
            _totalExp.value in accumulateExp(1) until accumulateExp(2) -> 1
            _totalExp.value in accumulateExp(2) until accumulateExp(3) -> 2
            _totalExp.value in accumulateExp(3) until accumulateExp(4) -> 3
            _totalExp.value in accumulateExp(4) until accumulateExp(5) -> 4
            _totalExp.value in accumulateExp(5) until accumulateExp(6) -> 5
            _totalExp.value!! >= accumulateExp(6) -> 6
            else -> 0
        }

        if (prevLevel < nowLevel) {
            prefEditor.putInt("level", nowLevel)
            prefEditor.apply()
            return true
        }

        return false
    }

    private fun accumulateExp(level: Int): Int {
        var accumulatedExp = 0
        for (i in 1..level) {
            accumulatedExp += levelRequiredExpList[i - 1]
        }
        return accumulatedExp
    }

    fun maxLevelAccumulatedExp(): Int {
        return maxLevelAccumulatedExp
    }
    fun requiredExp(level: Int): Int {
        return levelRequiredExpList[level -1]
    }

    fun getMaxLevel(): Int {
        return maxLevel
    }
}