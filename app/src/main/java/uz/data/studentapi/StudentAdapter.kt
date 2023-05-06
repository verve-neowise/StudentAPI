package uz.data.studentapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class StudentHolder(view: View) : ViewHolder(view)

class StudentAdapter(val students: List<Student>) : Adapter<StudentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentHolder(view)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        val student = students[position]
        val textview = holder.itemView as TextView
        textview.text = student.name
    }
}