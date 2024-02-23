package com.example.samgim.ui.mission_list

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.samgim.R
import com.example.samgim.Util.DateFomatter.Companion.dateFormat
import com.example.samgim.data.Points
import com.example.samgim.ui.DB.Todolist
import java.text.SimpleDateFormat
import java.util.Date

class TodoAdapter(val context: Context, var todos: List<Todolist>) :
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
        val todoId = itemView?.findViewById<TextView>(R.id.todo_id)

        val checkBox: View = check as View

        @SuppressLint("ResourceAsColor")
        fun bind(todolist: Todolist) {
            todoId?.text = todolist.listId.toString()
            title?.text = todolist.title
            category?.text = todolist.category
            regdate?.text = dateFormat(todolist.regdate)
            check?.isChecked = todolist.todo_check
            todoPoint?.text = "[${Points(context).getPoint(todolist.category)}pt]"

            when(todolist.category){
                "식사" -> category?.setBackgroundColor(ContextCompat.getColor(context, R.color.mealColor))
                "공부" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.studyColor))
                "운동" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.workoutColor))
                "수면" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.sleepColor))
                "기타" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.etcColor))
            }

//            if(check!!.isChecked){
//                checkBox.isEnabled = false
//            }
//
//            check.setOnCheckedChangeListener { buttonView, isChecked ->
//                if(isChecked) {
//                    Toast.makeText(context, "체크하였습니다.", Toast.LENGTH_SHORT).show()
//                    buttonView.isEnabled = false
//                }
//            }
        }
    }


    fun updateData(newTodoList: List<Todolist>) {
        this.todos = newTodoList
        notifyDataSetChanged()
    }


}