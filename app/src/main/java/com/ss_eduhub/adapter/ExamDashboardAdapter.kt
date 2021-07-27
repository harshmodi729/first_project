package com.ss_eduhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.databinding.LayExamDashboardItemBinding
import com.ss_eduhub.model.ExamDashboardItem

class ExamDashboardAdapter : RecyclerView.Adapter<ExamDashboardAdapter.ViewHolder>() {
    private var list = ArrayList<ExamDashboardItem>()

    class ViewHolder(val binding: LayExamDashboardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayExamDashboardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = list[position]
    }

    override fun getItemCount() = list.size

    fun addData(list: ArrayList<ExamDashboardItem>) {
        this.list = list
        notifyDataSetChanged()
    }
}