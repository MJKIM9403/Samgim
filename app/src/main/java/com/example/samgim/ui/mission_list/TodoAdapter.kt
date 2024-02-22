package com.example.samgim.ui.mission_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samgim.R
import com.example.samgim.ui.DB.Todolist
import java.text.SimpleDateFormat
import java.util.Date

class TodoAdapter(val context: Context, val todos: List<Todolist>) :
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
        val contents = itemView?.findViewById<TextView>(R.id.todo_contents)
        val category = itemView?.findViewById<TextView>(R.id.todo_category)
        val regdate = itemView?.findViewById<TextView>(R.id.todo_regdate)
        val check = itemView?.findViewById<TextView>(R.id.todo_check)

        fun bind(todolist: Todolist) {
            title?.text = todolist.title
            contents?.text = todolist.contents
            category?.text = todolist.category
            regdate?.text = dateFormat(todolist.regdate)
            check?.text = todolist.todo_check.toString()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(regdate: Date): String{
        val format = SimpleDateFormat("yyyy-MM-dd")
        val strDate = regdate.let { format.format(it) }
        return strDate
    }


}