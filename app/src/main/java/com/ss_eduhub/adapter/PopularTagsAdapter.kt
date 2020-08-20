package com.ss_eduhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.R
import com.ss_eduhub.model.PopularTagCategoryItem
import kotlinx.android.synthetic.main.list_item_tags.view.*

class PopularTagsAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<PopularTagsAdapter.ViewHolder>() {

    private var alPopularTagCategoryItem = ArrayList<PopularTagCategoryItem>()
    var onTagItemClickListener: ((item: String, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_tags, parent, false)
        )
    }

    override fun getItemCount() = alPopularTagCategoryItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagName.text = alPopularTagCategoryItem[position].categoryName
        holder.itemView.setOnClickListener {
            onTagItemClickListener?.invoke(alPopularTagCategoryItem[position].categoryName, alPopularTagCategoryItem[position].categoryId)
        }
    }

    fun addData(alPopularTagCategoryItem: ArrayList<PopularTagCategoryItem>) {
        this.alPopularTagCategoryItem = alPopularTagCategoryItem
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tagName: AppCompatTextView = view.tvTagName
    }
}