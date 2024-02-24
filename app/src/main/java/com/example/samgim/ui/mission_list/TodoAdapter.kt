package com.example.samgim.ui.mission_list

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.samgim.R
import com.example.samgim.Util.DateFomatter.Companion.dateFormat
import com.example.samgim.data.Points
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.DB.TodolistDB
import com.example.samgim.ui.mission_edit.EditAdapter

class TodoAdapter(val context: Context, var todos: List<Todolist>, private val listener: EditAdapter.OnItemClickListener) :
    RecyclerView.Adapter<TodoAdapter.Holder>() {
        lateinit var pref: SharedPreferences
        lateinit var prefEditor: SharedPreferences.Editor
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        pref = context.getSharedPreferences("state",Activity.MODE_PRIVATE)
        prefEditor = pref.edit()
        val view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(todos[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val title = itemView?.findViewById<TextView>(R.id.todo_title)
        val category = itemView?.findViewById<TextView>(R.id.todo_category)
        val regdate = itemView?.findViewById<TextView>(R.id.todo_regdate)
        val check = itemView?.findViewById<CheckBox>(R.id.check1)
        val todoPoint = itemView?.findViewById<TextView>(R.id.todo_point)

        val checkBox: View = check as View

        @SuppressLint("ResourceAsColor")
        fun bind(todolist: Todolist) {
            itemView.setOnClickListener {
                listener.onItemClick(todolist)
            }

            val selectPoint = Points(context).getPoint(todolist.category)

            title?.text = todolist.title
            category?.text = todolist.category
            regdate?.text = dateFormat(todolist.regdate)
            check?.isChecked = todolist.todo_check
            todoPoint?.text = "[${selectPoint}pt]"

            when(todolist.category){
                "식사" -> category?.setBackgroundColor(ContextCompat.getColor(context, R.color.mealColor))
                "공부" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.studyColor))
                "운동" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.workoutColor))
                "수면" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.sleepColor))
                "기타" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.etcColor))
            }

            check!!.setOnCheckedChangeListener { buttonView, isChecked ->
                val todoDB = TodolistDB.getInstance(context)
                var totalExp = pref.getInt("totalExp",0)
                var nextLevelRequiredExp = pref.getInt("nextLevelRequiredExp",10)

                Thread{
                    todolist.todo_check = isChecked
                    todoDB.getDAO().updateTodos(todolist)
                }.start()

                if(isChecked) {
                    totalExp += selectPoint
                }else {
                    totalExp -= selectPoint
                }

                prefEditor.putInt("totalExp", totalExp)
                if(totalExp >= nextLevelRequiredExp){
                    levelUpEvent()
                }
                prefEditor.apply()
            }
        }
    }

    fun levelUpEvent() {
        val name = pref.getString("name","김밥이")
        val image = ImageView(context)
        image.setImageResource(R.drawable.damgomb)

        val builder = AlertDialog.Builder(context)
        builder.setTitle("레벨 업!")
        builder.setView(image)
        builder.setMessage("오잉...? ${name}의 상태가...?!")
        builder.setNeutralButton("상태를 보러가기"){ dialog, which ->

        }
        builder.show()
    }


    fun updateData(newTodoList: List<Todolist>) {
        this.todos = newTodoList
        notifyDataSetChanged()
    }
}