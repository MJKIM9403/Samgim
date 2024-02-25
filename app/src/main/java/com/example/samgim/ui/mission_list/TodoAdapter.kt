package com.example.samgim.ui.mission_list

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
import com.example.samgim.ui.todolist.OnCheckBoxClick

class TodoAdapter(val context: Context,
                  var todos: List<Todolist>,
                  private val listener: EditAdapter.OnItemClickListener,
                  private val checkListener: OnCheckBoxClick) :
    RecyclerView.Adapter<TodoAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
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

        @SuppressLint("ResourceAsColor")
        fun bind(todolist: Todolist) {
            itemView.setOnClickListener {
                listener.onItemClick(todolist)
            }

            val selectPoint: Int = Points(context).getPoint(todolist.category)

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
                var point: Int = 0

                Thread{
                    todolist.todo_check = isChecked
                    todoDB.getDAO().updateTodos(todolist)
                    Log.d("test","${todolist.title}: ${todolist.todo_check}")
                }.start()

                if(isChecked) {
                    point = selectPoint
                }else {
                    point = -1 * selectPoint
                }

                checkListener.checkTodo(point)
            }
        }
    }


    fun updateData(newTodoList: List<Todolist>) {
        this.todos = newTodoList
        notifyDataSetChanged()
    }
}