package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import com.hmatter.first_project.model.VideoCategoryItem
import kotlinx.android.synthetic.main.lay_video_category_item.view.*

class VideoCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<VideoCategoryAdapter.ViewHolder>() {

    private var alVideoCategoryItem = ArrayList<VideoCategoryItem>()
    var onCategorySelectedListener: ((item: VideoCategoryItem, isChecked: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lay_video_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.chkVideoCategory.isChecked = true
        }
        holder.chkVideoCategory.text = alVideoCategoryItem[position].categoryName
        holder.chkVideoCategory.setOnCheckedChangeListener { _, b ->
            onCategorySelectedListener?.invoke(alVideoCategoryItem[position], b)
        }
    }

    override fun getItemCount() = alVideoCategoryItem.size

    fun addData(alVideoCategoryItem: ArrayList<VideoCategoryItem>) {
        this.alVideoCategoryItem = alVideoCategoryItem
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chkVideoCategory: AppCompatCheckBox = itemView.chkVideoCategory
    }
}
