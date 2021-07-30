package com.ss_eduhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.databinding.LayCourseTestItemBinding
import com.ss_eduhub.model.CourseTestItem

class CourseTestAdapter(private val listener: OnCardClickListener) :
    RecyclerView.Adapter<CourseTestAdapter.ViewHolder>() {
    private var list = ArrayList<CourseTestItem>()

    class ViewHolder(val binding: LayCourseTestItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayCourseTestItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = list[position]

        holder.binding.root.setOnClickListener {
            listener.onCourseSelected(list[position])
        }
    }

    override fun getItemCount() = list.size

    interface OnCardClickListener {
        fun onCourseSelected(item: CourseTestItem)
    }

    fun addData(list: ArrayList<CourseTestItem>) {
        this.list = list
        notifyDataSetChanged()
    }
}