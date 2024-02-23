package com.example.samgim.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.samgim.R
import com.example.samgim.Util.DateFomatter.Companion.dateFormat
import com.example.samgim.ui.DB.Todolist

class HistoryAdapter(val context: Context, var todos: List<Todolist>) :
    RecyclerView.Adapter<HistoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
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
        val contents = itemView?.findViewById<TextView>(R.id.todo_contents)
        val category = itemView?.findViewById<TextView>(R.id.todo_category)
        val regdate = itemView?.findViewById<TextView>(R.id.todo_regdate)
        val check = itemView?.findViewById<TextView>(R.id.todo_check)

        @SuppressLint("ResourceAsColor")
        fun bind(todolist: Todolist) {
            title?.text = todolist.title
            contents?.text = todolist.contents
            category?.text = todolist.category
            regdate?.text = dateFormat(todolist.regdate)
            check?.text = todolist.todo_check.toString()

            when(todolist.category){
                "식사" -> category?.setBackgroundColor(ContextCompat.getColor(context, R.color.mealColor))
                "공부" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.studyColor))
                "운동" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.workoutColor))
                "수면" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.sleepColor))
                "기타" -> category?.setBackgroundColor(ContextCompat.getColor(context,R.color.etcColor))
            }
        }
    }


    fun updateData(newTodoList: List<Todolist>) {
        this.todos = newTodoList
        notifyDataSetChanged()
    }


}