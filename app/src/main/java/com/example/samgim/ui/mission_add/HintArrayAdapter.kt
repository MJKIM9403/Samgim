import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.samgim.R

class HintArrayAdapter(private val context: Context, private val items: List<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false)
            holder = ViewHolder()
            holder.textView = view.findViewById(android.R.id.text1)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        if (position == 0) {
            holder.textView?.text = context.getString(R.string.select_todo)
        } else {
            holder.textView?.text = items[position]
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
            holder = ViewHolder()
            holder.textView = view.findViewById(android.R.id.text1)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        if (position == 0) {
            // 드롭다운 목록에서 힌트 가리기
            holder.textView?.text = ""
            view.layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1) // hint 높이 1을 줘서 없애기
            view.visibility = View.GONE
        } else {
            holder.textView?.visibility = View.VISIBLE
            holder.textView?.text = items[position]
            view.layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150) // hint가 아닌 경우엔 높이 150
            view.visibility = View.VISIBLE
        }


        return view
    }

    private class ViewHolder {
        var textView: TextView? = null
    }
}