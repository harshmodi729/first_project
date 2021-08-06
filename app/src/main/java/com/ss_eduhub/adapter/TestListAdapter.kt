package com.ss_eduhub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.databinding.LayTestListItemBinding
import com.ss_eduhub.model.TestListItem

class TestListAdapter(private val listener: OnCardClickListener) :
    RecyclerView.Adapter<TestListAdapter.ViewHolder>() {
    private var list = ArrayList<TestListItem>()

    inner class ViewHolder(val binding: LayTestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var chkAttemptResume = MutableLiveData<Boolean>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayTestListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = list[position]
        var text = "Attempt"
        if (list[position].attempted) {
            text = "Resume"
        }
        holder.binding.btnAttemptResume.text = text
        holder.binding.btnAttemptResume.isChecked = list[position].attempted
        holder.binding.btnAttemptResume.setOnCheckedChangeListener { view, b ->
            if (b) view.text = "Resume" else view.text = "Attempt"
        }

        holder.binding.root.setOnClickListener {
            listener.onCourseSelected(list[position])
        }
    }

    override fun getItemCount() = list.size

    interface OnCardClickListener {
        fun onCourseSelected(item: TestListItem)
    }

    fun addData(list: ArrayList<TestListItem>) {
        this.list = list
        notifyDataSetChanged()
    }
}