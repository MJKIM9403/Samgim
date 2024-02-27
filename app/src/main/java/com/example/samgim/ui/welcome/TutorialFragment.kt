package com.example.samgim.ui.welcome

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.samgim.R
import com.example.samgim.databinding.FragmentTutorialBinding
import com.example.samgim.ui.importance.SettingsActivity


class TutorialFragment : Fragment() {
    private var image: Int? = null
    private var text: String? = null

    private var _binding: FragmentTutorialBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            image = it.getInt("image", 0)
            text = it.getString("text", "")
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireActivity().getSharedPreferences("access", Activity.MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = pref.edit()

        binding.imageView.setImageResource(image!!)
        binding.textView.text = text

        // 4번째 페이지에만 버튼 보이도록 설정
        if (arguments?.getInt("position", 0) == 3) {
            binding.tutorialButton.visibility = View.VISIBLE
            binding.tutorialButton.setOnClickListener {
                prefEditor.putBoolean("isFirst", false)
                prefEditor.apply()
                // 버튼 클릭 시 수행할 작업 추가
                val intent = Intent(requireContext(), SettingsActivity::class.java)
                intent.putExtra("isFirst", true)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        } else {
            binding.tutorialButton.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun newInstance(image: Int, text: String, position: Int) =
            TutorialFragment().apply{
                arguments = Bundle().apply {
                    putInt("image", image)
                    putString("text", text)
                    putInt("position", position)
                }
            }
    }
}