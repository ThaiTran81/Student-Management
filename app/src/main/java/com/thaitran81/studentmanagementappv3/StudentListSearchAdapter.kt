package com.thaitran81.studentmanagementappv3

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.thaitran81.studentmanagementappv3.model.StudentModel
import java.util.*
import kotlin.collections.ArrayList

class StudentListSearchAdapter(
    val context: Activity,
    studentLst: List<StudentModel>
) : ArrayAdapter<StudentModel>(context, 0, studentLst) {

    var studentLstFull: List<StudentModel>
    init {
        studentLstFull = ArrayList(studentLst)
    }

    override fun getFilter(): Filter {
        super.getFilter()
        return studentFilter
    }

    private var studentFilter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val result = FilterResults()
            val suggestions = ArrayList<StudentModel>()

            if (p0 == null || p0.length == 0) {
                suggestions.addAll(studentLst);
            } else {
                val filterpattern = p0.toString().lowercase().trim()

                for (item in studentLstFull) {
                    if (item.toString().lowercase().contains(filterpattern)) {
                        suggestions.add(item)
                    }
                }
            }

            result.values = suggestions
            result.count = suggestions.size

            return result

        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            clear()
            addAll(p1!!.values as MutableList<StudentModel>)
            notifyDataSetChanged()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            val resultValue = resultValue as StudentModel
            return ""
        }
    }

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView: View = inflater.inflate(R.layout.student_list_item, null, false)
        val tvClass = rowView.findViewById(R.id.tvClassId) as TextView
        val tvFullname = rowView.findViewById(R.id.tvFullname) as TextView
        val tvDob = rowView.findViewById(R.id.tvDobGender) as TextView
        val item = getItem(position)
        tvFullname.text = item?.fullname
        tvClass.text = item?.classID
        tvDob.text = "${item?.dob} - ${item?.gender}"

        return rowView
    }
}



