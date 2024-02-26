package com.example.samgim.ui.welcome

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

import com.example.samgim.R
import me.relex.circleindicator.CircleIndicator

class TutorialActivity : AppCompatActivity() {
    private lateinit var vpAdapter: CustomPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var indicator: CircleIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tutorial_main)

        // XML 파일에서 정의한 뷰의 ID를 가져옴
        viewPager = findViewById(R.id.viewpager)
        indicator = findViewById(R.id.indicator)

        // ViewPager2 어댑터 생성 및 설정
        vpAdapter = CustomPagerAdapter(supportFragmentManager)
        viewPager.adapter = vpAdapter

        // CircleIndicator에 ViewPager2 연결
        indicator.setViewPager(viewPager)
    }

    // ViewPager2에 사용될 FragmentStatePagerAdapter 구현
    class CustomPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val PAGENUMBER = 4

        override fun getCount(): Int {
            return PAGENUMBER
        }

        override fun getItem(position: Int): Fragment {
            return TutorialFragment.newInstance(
                when (position) {
                    0 -> R.drawable.tutorial01
                    1 -> R.drawable.tutorial02
                    2 -> R.drawable.img02
                    3 -> R.drawable.tutorial004
                    else -> R.drawable.img00
                },
                when (position) {
                    0 -> "자신만의 중요도를 설정!"
                    1 -> "카테고리를 선택하고\n매일 매일 오늘의 해야할 일을 작성!"
                    2 -> "해야할 일을 완료 후 체크!"
                    3 -> "당신만의 쌀알이 진화!"
                    else -> "page00"
                },
                position
            )
        }
    }
}

