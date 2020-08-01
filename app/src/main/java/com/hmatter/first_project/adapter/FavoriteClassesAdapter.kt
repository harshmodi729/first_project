package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hmatter.first_project.R
import com.hmatter.first_project.model.FavoriteCLassesItem
import kotlinx.android.synthetic.main.lay_favorite_classes_item.view.*

class FavoriteClassesAdapter(private val context: Context) :
    RecyclerView.Adapter<FavoriteClassesAdapter.ViewHolder>() {
    private var alFavoriteClassesItem = ArrayList<FavoriteCLassesItem>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivClass = itemView.ivClass as AppCompatImageView
        val tvTutorName = itemView.tvTutorName as AppCompatTextView
        val tvDescription = itemView.tvDescription as AppCompatTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.lay_favorite_classes_item, parent, false)
        )
    }

    override fun getItemCount() = alFavoriteClassesItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alFavoriteClassesItem[position]
        Glide
            .with(context)
            .load(item.image_path)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.ivClass)
        holder.tvTutorName.text = item.tutor_name
        holder.tvDescription.text = item.description
    }

    fun addData(alFavoriteCLassesItem: ArrayList<FavoriteCLassesItem>) {
        this.alFavoriteClassesItem = alFavoriteCLassesItem
        notifyDataSetChanged()
    }
}