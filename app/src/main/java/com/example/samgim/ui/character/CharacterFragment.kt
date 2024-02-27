package com.example.samgim.ui.character

import android.app.Activity
import android.app.Application
import android.content.Context
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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.samgim.R
import com.example.samgim.databinding.FragmentCharacterBinding

class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CharacterViewModel
    private lateinit var viewModelFactory: CharacterViewModelFactory
    private lateinit var context: FragmentActivity

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
        context = requireActivity()

        viewModelFactory = CharacterViewModelFactory(context.application)
        viewModel = ViewModelProvider(context, viewModelFactory).get(CharacterViewModel::class.java)

        setExp()
        setImg()
        setName()

        setLevelObserver()

        editName()

    }
    private fun setLevelObserver() {
        viewModel.level.observe(viewLifecycleOwner, Observer {
            Log.d("test", "levelupevent, ${viewModel.level.value}")
        })
    }

    private fun setName() {
        val name: EditText = binding.characterName
        name.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),android.R.color.transparent)
        viewModel.characterName.observe(viewLifecycleOwner, Observer {
            name.setText(it)
        })
    }
    private fun setImg() {
        val characterImg: ImageView = binding.characterImg
        viewModel.characterImage.observe(viewLifecycleOwner, Observer {
            characterImg.setImageResource(it)
        })
    }
    private fun setExp() {
        val todayExp: TextView = binding.textViewTodayExpVal
        val progressBarExp: ProgressBar = binding.progressBarExp
        val download: TextView = binding.textViewDownload

        viewModel.todayExp.observe(viewLifecycleOwner, Observer {
            todayExp.text = it.toString()
        })

        viewModel.currentExpToShow.observe(viewLifecycleOwner, Observer {
            if(viewModel.totalExp.value!! < viewModel.maxLevelAccumulatedExp()){
                progressBarExp.progress = it
                download.text = "레벨업까지 ${(viewModel.nextLevelRequiredExp.value)?.minus(it)}점!"
                Log.d("test", "현재 경험치: $it")
            }else {
                progressBarExp.progress = viewModel.requiredExp(viewModel.getMaxLevel())
                download.text = "최대 레벨입니다."
            }
        })
        viewModel.nextLevelRequiredExp.observe(viewLifecycleOwner, Observer {
            if(viewModel.totalExp.value!! < viewModel.maxLevelAccumulatedExp()){
                progressBarExp.max = it
                Log.d("test", "다음레벨 요구 경험치: $it")
            }else {
                progressBarExp.max = viewModel.requiredExp(viewModel.getMaxLevel())
            }
        })
    }

    private fun editName() {
        val name: EditText = binding.characterName
        val editBtn: ImageButton = binding.editBtn

        editBtn.setOnClickListener {
            if(name.isEnabled){
                val inputName = name.text.toString()
                if(inputName.length > 10){
                    Toast.makeText(requireActivity(),"이름을 10자 이내로 설정하여 주십시오.",Toast.LENGTH_SHORT).show()
                }else if(inputName.trim().isEmpty()){
                    Toast.makeText(requireActivity(),"이름은 비워둘 수 없습니다.",Toast.LENGTH_SHORT).show()
                }else {
                    viewModel.updateCharacterName(name.text.toString())
                    Log.d("name",name.text.toString())
                    name.isEnabled = false
                    Toast.makeText(context,"이름이 변경되었습니다.",Toast.LENGTH_SHORT).show()
                    name.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),android.R.color.transparent)
                }
            }else {
                name.isEnabled = true
                name.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),android.R.color.darker_gray)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}