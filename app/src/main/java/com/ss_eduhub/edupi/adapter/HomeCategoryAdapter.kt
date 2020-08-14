package com.ss_eduhub.edupi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.model.PopularTagCategoryItem
import kotlinx.android.synthetic.main.lay_categories_home.view.*

class HomeCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    private var alCategories = ArrayList<PopularTagCategoryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_categories_home, parent, false
        )
    )

    override fun getItemCount() = if (alCategories.size > 5) 4 else alCategories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCategory.text = alCategories[position].categoryName
    }

    fun addData(alCategories: ArrayList<PopularTagCategoryItem>) {
        this.alCategories = alCategories
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: AppCompatTextView = view.tvCategory
    }
}