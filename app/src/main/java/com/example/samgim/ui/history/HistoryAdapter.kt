package com.example.samgim.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.example.samgim.data.Points
import com.example.samgim.ui.DB.Todolist
import com.example.samgim.ui.detail.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class HistoryAdapter(val context: Context,
                     var todos: List<Todolist>,
                     private val listener: OnItemClickListener) :

    RecyclerView.Adapter<HistoryAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = getRecyclerViewItemHeight(context, view)
        view.layoutParams = layoutParams
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
        val contents = itemView?.findViewById<TextView>(R.id.todo_memo)
        val category = itemView?.findViewById<TextView>(R.id.todo_category)
        val regdate = itemView?.findViewById<TextView>(R.id.todo_regdate)
        val check = itemView?.findViewById<CheckBox>(R.id.checkBox)
        val categoryImg = itemView?.findViewById<CircleImageView>(R.id.todo_category_img)

        @SuppressLint("ResourceAsColor")
        fun bind(todolist: Todolist) {
            itemView.setOnClickListener {
                listener.onItemClick(todolist)
            }



            title?.text = todolist.title
            contents?.text = todolist.contents
            category?.text = todolist.category
            regdate?.text = dateFormat(todolist.regdate)
            check?.isChecked = todolist.todo_check

            when (todolist.category) {
                "식사" -> {
                    categoryImg?.apply {
                        setCircleBackgroundColorResource(R.color.mealColor)
                        setImageResource(R.drawable.mealicon)
                        borderColor = Color.parseColor("#FB4D3D")
                    }
                }

                "공부" -> {
                    categoryImg?.apply {
                        setCircleBackgroundColorResource(R.color.studyColor)
                        setImageResource(R.drawable.editpen)
                        borderColor = Color.parseColor("#CA1551")
                    }


                }

                "운동" -> {
                    categoryImg?.apply {
                        setCircleBackgroundColorResource(R.color.workoutColor)
                        setImageResource(R.drawable.exerciseicon)
                        borderColor = Color.parseColor("#345995")
                    }
                }

                "수면" -> {
                    categoryImg?.apply {
                        setCircleBackgroundColorResource(R.color.sleepColor)
                        setImageResource(R.drawable.bedtimeicon)
                        borderColor = Color.parseColor("#EAC435")
                    }


                }

                "기타" -> {
                    categoryImg?.apply {
                        setCircleBackgroundColorResource(R.color.etcColor)
                        setImageResource(R.drawable.etcicon)
                        borderColor = Color.parseColor("#03CEA4")
                    }
                }
            }
        }
    }



    fun updateData(newTodoList: List<Todolist>) {
        this.todos = newTodoList
        notifyDataSetChanged()
    }

    fun getRecyclerViewItemHeight(context: Context, itemView: View): Int {
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            context.resources.displayMetrics.widthPixels,
            View.MeasureSpec.EXACTLY
        )
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        itemView.measure(widthMeasureSpec, heightMeasureSpec)
        return itemView.measuredHeight
    }

    interface OnItemClickListener {
        fun onItemClick(todolist: Todolist)
    }
}