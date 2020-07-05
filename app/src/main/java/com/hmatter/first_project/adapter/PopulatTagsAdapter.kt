package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
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

    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagName?.text = tagList.get(position)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, tagList.get(position), Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagName = view.tvTagName
    }
}