package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import kotlinx.android.synthetic.main.lay_popular_classes_item.view.*

class PopularClassesAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<PopularClassesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_popular_classes_item, parent, false
        )
    )

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ratingClass.rating = 4.5F
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ratingClass: RatingBar = view.ratingClass
    }
}