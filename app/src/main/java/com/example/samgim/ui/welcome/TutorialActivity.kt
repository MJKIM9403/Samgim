//package com.example.samgim.ui.welcome
//
//import android.os.Bundle
//import android.os.PersistableBundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentStatePagerAdapter
//import androidx.viewpager2.widget.ViewPager2
//import com.example.samgim.R
//import me.relex.circleindicator.CircleIndicator
//
//class TutorialActivity : AppCompatActivity() {
//    private lateinit var vpAdapter: CustomPagerAdapter
//    private lateinit var viewPager: ViewPager2
//    private lateinit var indicator: CircleIndicator
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.tutorial_main)
//
//        // XML 파일에서 정의한 뷰의 ID를 가져옴
//        viewPager = findViewById(R.id.viewpager)
//        indicator = findViewById(R.id.indicator)
//
//        // ViewPager2 어댑터 생성 및 설정
//        vpAdapter = CustomPagerAdapter(supportFragmentManager)
//        viewPager.adapter = vpAdapter
//
//        // CircleIndicator에 ViewPager2 연결
//        indicator.setViewPager(viewPager)
//    }
//
//    // ViewPager2에 사용될 FragmentStatePagerAdapter 구현
//    class CustomPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//        private val PAGENUMBER = 4
//
//        override fun getCount(): Int {
//            return PAGENUMBER
//        }
//
//        override fun getItem(position: Int): Fragment {
//            return when (position) {
//                0 -> TutorialFragment.newInstance(R.drawable.img00, "page00")
//                1 -> TutorialFragment.newInstance(R.drawable.img01, "page01")
//                2 -> TutorialFragment.newInstance(R.drawable.img02, "page02")
//                3 -> TutorialFragment.newInstance(R.drawable.img03, "page03")
//                else -> TutorialFragment.newInstance(R.drawable.img00, "page00")
//            }
//        }
//    }
//}
//
