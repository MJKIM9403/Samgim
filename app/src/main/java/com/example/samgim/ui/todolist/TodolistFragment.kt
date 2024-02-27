package com.example.samgim.ui.todolist

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samgim.R
import com.example.samgim.Util.DateFomatter
import com.example.samgim.data.Points
import com.example.samgim.databinding.FragmentTodolistBinding
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.character.CharacterViewModel
import com.example.samgim.ui.character.CharacterViewModelFactory
import com.example.samgim.ui.mission_add.MissionAddActivity
import com.example.samgim.ui.mission_detail.MissionDetailActivity
import com.example.samgim.ui.mission_edit.EditAdapter
import com.example.samgim.ui.mission_list.TodoAdapter
import java.util.Date

class TodolistFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var todoDB : TodolistDB? = null
    private var todoList =  listOf<Todolist>()
    lateinit var tAdapter : TodoAdapter

    private lateinit var viewModel: CharacterViewModel
    private lateinit var viewModelFactory: CharacterViewModelFactory

    private lateinit var pref: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today: Date = format.parse(DateFomatter.dateFormat(Date()))

        pref = requireActivity().getSharedPreferences("state", Activity.MODE_PRIVATE)
        prefEditor = pref.edit()

        viewModelFactory = CharacterViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CharacterViewModel::class.java)

        todoDB = TodolistDB.getInstance(requireActivity())
        tAdapter = TodoAdapter(requireActivity(), todoList, object : EditAdapter.OnItemClickListener {
            override fun onItemClick(todolist: Todolist) {
                val intent = Intent(activity, MissionDetailActivity::class.java).apply {
                    putExtra("todoId", todolist.listId)
                    // 필요한 다른 데이터를 putExtra로 추가
                    Log.d("test",todolist.listId.toString())
                }
                startActivity(intent)
            }
        }, onClickCheckBox = { point ->
                Log.d("test","checkTodo: click $point")
                val prevLevel: Int = viewModel.level.value!!
                viewModel.updateState(point)
                val nowLevel: Int = viewModel.level.value!!
                if(nowLevel > prevLevel){
                    levelUpEvent()
                }
            }
        )


        Thread {
            todoList = todoDB?.getDAO()?.getAllByRegdate(today)!!
            setRecyclerView()
        }.start()

        binding.mAddBtn.setOnClickListener {
            val intent = Intent(requireActivity(), MissionAddActivity::class.java)
            startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setRecyclerView() {
        activity?.runOnUiThread(
            Runnable {
                tAdapter.updateData(todoList)
                tAdapter.notifyDataSetChanged()

                binding.recyclerView.adapter = tAdapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                binding.recyclerView.setHasFixedSize(true)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        refreshTodoList()
    }

    override fun onStop() {
        val todayExp = calcTodayExp()
        prefEditor.putInt("todayExp", todayExp)
        prefEditor.apply()
        viewModel.updateTodayExp()
        super.onStop()
    }

    private fun refreshTodoList() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val today: Date = format.parse(DateFomatter.dateFormat(Date()))

        Thread {
            todoList = todoDB?.getDAO()?.getAllByRegdate(today)!!
            // UI 업데이트는 메인 스레드에서 진행되어야 함
            activity?.runOnUiThread {
                tAdapter.updateData(todoList)
                tAdapter.notifyDataSetChanged()
            }
        }.start()
    }
    fun levelUpEvent() {
        val name = viewModel.characterName.value
        val image = ImageView(requireActivity())
        image.setImageResource(R.drawable.damgomb)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("레벨 업!")
        builder.setView(image)
        builder.setMessage("오잉...? ${name}의 상태가...?!")
        builder.setNeutralButton("상태를 보러가기"){ dialog, which ->
            todoToChar()
        }
        builder.show()
    }

    fun todoToChar() {
        val navController = view?.findNavController()
        navController?.navigate(R.id.action_navigation_todolist_to_navigation_character)
    }

    fun calcTodayExp(): Int {
        var todayExp = 0
        todoList.forEach { todo ->
            if(todo.todo_check){
                todayExp += Points(requireActivity()).getPoint(todo.category)
            }
        }
        Log.d("test","today: $todayExp")
        return todayExp
    }


}