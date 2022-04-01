package com.thaitran81.studentmanagementappv3

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thaitran81.studentmanagementappv3.model.DbModeApp
import com.thaitran81.studentmanagementappv3.model.StudentModel


class StudentAdapter(
    val context: Context,
    val items: List<StudentModel>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var typeLayout = R.layout.student_list_item
        if (DbModeApp.LAYOUT_MODE == 1) {
            typeLayout = R.layout.student_grid_item
        }
        val view = inflater.inflate(typeLayout, parent, false)

        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items.get(position)

        holder.tvFullname.text = item.fullname
        holder.tvClassId.text = item.classID
        holder.tvDobGender.text = "${item.dob} - ${item.gender}"

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        // Holds the TextView that will add each item to
        var tvFullname: TextView
        var tvClassId: TextView
        var tvDobGender: TextView

        init {
            if (DbModeApp.LAYOUT_MODE == 0) {
                tvFullname = view.findViewById<TextView>(R.id.tvFullname)
                tvClassId = view.findViewById<TextView>(R.id.tvClassId)
                tvDobGender = view.findViewById<TextView>(R.id.tvDobGender)
            } else {
                tvFullname = view.findViewById<TextView>(R.id.tvFullname_grid)
                tvClassId = view.findViewById<TextView>(R.id.tvClassId_grid)
                tvDobGender = view.findViewById<TextView>(R.id.tvDobGender_grid)
            }

            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}
