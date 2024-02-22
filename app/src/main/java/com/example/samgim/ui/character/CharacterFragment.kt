package com.example.samgim.ui.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.samgim.R
import com.example.samgim.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val levelReqExp: List<Int> = listOf(0, 10, 20, 40, 60, 80)
    var totalExp: Int = 210 /* 지금까지 누적된 총 경험치*/ // TODO: sharedPreference에서 총 경험치 읽어오기
    var level: Int = checkLevel() /* 현재 레벨 */
    val maxLevel: Int = levelReqExp.size
    var nextLevelReqExp: Int = when{
        level < maxLevel -> levelReqExp.get(level)
        else -> levelReqExp.last()
    } /* 다음 레벨까지 요구 경험치 */
    var nowLevelReqExp: Int = accumulateExp(level) /* 현재 레벨까지 누적 요구 경험치 */
    var showExp: Int = totalExp - nowLevelReqExp /* 프로그래스 바에서 보이는 현재 경험치 */

    private fun accumulateExp(level: Int): Int {
        var accumExp = 0
        for(i: Int in 1..level){
            accumExp += levelReqExp.get(i-1)
        }
        return accumExp
    }

    private fun checkLevel(): Int {
        val level: Int = when{
            totalExp in accumulateExp(1) until accumulateExp(2) -> 1
            totalExp in accumulateExp(2) until accumulateExp(3) -> 2
            totalExp in accumulateExp(3) until accumulateExp(4) -> 3
            totalExp in accumulateExp(4) until accumulateExp(5) -> 4
            totalExp in accumulateExp(5) until accumulateExp(6) -> 5
            totalExp >= accumulateExp(6) -> 6
            else -> 0
        }

        return level
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todayExp: TextView = binding.textViewTodayExpVal
        val progressBarExp: ProgressBar = binding.progressBarExp
        val download: TextView = binding.textViewDownload

        val todayExpVal: Int = 10 // TODO: 오늘 작성한 리스트 중 완료된 값의 점수만 합산
        todayExp.text = "$todayExpVal 점"
        if(totalExp < accumulateExp(maxLevel)){
            download.text = "레벨업까지 ${nextLevelReqExp - showExp}점!"
            progressBarExp.max = nextLevelReqExp
            progressBarExp.progress = showExp
        }else {
            download.text = "최대 레벨입니다."
            progressBarExp.max = levelReqExp.get(maxLevel-1)
            progressBarExp.progress = levelReqExp.get(maxLevel-1)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}