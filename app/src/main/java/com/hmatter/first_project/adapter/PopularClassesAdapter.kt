package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import com.hmatter.first_project.model.PopularClassItem
import kotlinx.android.synthetic.main.lay_popular_classes_item.view.*

class PopularClassesAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<PopularClassesAdapter.ViewHolder>() {

    private var alPopularClassItem = ArrayList<PopularClassItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_popular_classes_item, parent, false
        )
    )

    override fun getItemCount() = alPopularClassItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alPopularClassItem[position]
        holder.tvClassName.text = item.tutorName
        holder.tvPrice.text = item.classPrice
        holder.tvClassDetail.text = item.classDetail
        var categories = ""
        for (categoryItem in item.classCategory) {
            categories =
                categories.plus("${context.getString(R.string.bullet)} ${categoryItem.categoryName} ")
        }
        holder.tvClassTags.text = categories
        holder.ratingClass.rating = item.classRating
        holder.tvTotalVideos.text = "${item.totalVideos}".plus(" Videos")
    }

    fun addData(alPopularClassItem: ArrayList<PopularClassItem>) {
        this.alPopularClassItem = alPopularClassItem
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvClassName: AppCompatTextView = view.tvClassName
        val tvPrice: AppCompatTextView = view.tvPrice
        val tvClassDetail: AppCompatTextView = view.tvClassDetail
        val tvClassTags: AppCompatTextView = view.tvClassTags
        val ratingClass: RatingBar = view.ratingClass
        val tvTotalVideos: AppCompatTextView = view.tvTotalVideos
    }
}