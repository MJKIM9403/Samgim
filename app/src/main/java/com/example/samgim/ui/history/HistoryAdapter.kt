package com.example.samgim.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.samgim.R
import com.example.samgim.Util.DateFomatter.Companion.dateFormat
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.detail.DetailActivity
import org.w3c.dom.Text

class HistoryAdapter(val context: Context,
                     var todos: List<Todolist>,
                     private val listener: OnItemClickListener) :

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
        val listId = itemView?.findViewById<TextView>(R.id.todo_id)
        val title = itemView?.findViewById<TextView>(R.id.todo_title)
        val contents = itemView?.findViewById<TextView>(R.id.todo_memo)
        val category = itemView?.findViewById<TextView>(R.id.todo_category)
        val regdate = itemView?.findViewById<TextView>(R.id.todo_regdate)
        val check = itemView?.findViewById<CheckBox>(R.id.checkBox)

        @SuppressLint("ResourceAsColor")
        fun bind(todolist: Todolist) {
            itemView.setOnClickListener {
                listener.onItemClick(todolist)
            }

            listId?.text = todolist.listId.toString()
            title?.text = todolist.title
            contents?.text = todolist.contents
            category?.text = todolist.category
            regdate?.text = dateFormat(todolist.regdate)
            check?.isChecked = todolist.todo_check


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

    interface OnItemClickListener {
        fun onItemClick(todolist: Todolist)
    }
}