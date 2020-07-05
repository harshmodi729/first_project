package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import com.hmatter.first_project.extension.makeToast
import kotlinx.android.synthetic.main.list_item_tags.view.*

class PopularTagsAdapter(
    private val context: Context,
    private val tagList: ArrayList<String>
) :
    RecyclerView.Adapter<PopularTagsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_tags, parent, false)
        )
    }

    override fun getItemCount() = tagList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagName.text = tagList[position]
        holder.itemView.setOnClickListener {
            context.makeToast(tagList[position])
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagName: AppCompatTextView = view.tvTagName
    }
}