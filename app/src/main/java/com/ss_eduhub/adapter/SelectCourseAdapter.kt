package com.ss_eduhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.databinding.LaySelectCourseItemBinding
import com.ss_eduhub.model.CourseItem

class SelectCourseAdapter(private val listener: OnCardClickListener) :
    RecyclerView.Adapter<SelectCourseAdapter.ViewHolder>() {
    private var list = ArrayList<CourseItem>()

    class ViewHolder(val binding: LaySelectCourseItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LaySelectCourseItemBinding.inflate(
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
        fun onCourseSelected(item: CourseItem)
    }

    fun addData(list: ArrayList<CourseItem>) {
        this.list = list
        notifyDataSetChanged()
    }
}