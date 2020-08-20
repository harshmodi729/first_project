package com.ss_eduhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.R
import com.ss_eduhub.extension.loadImage
import com.ss_eduhub.model.PopularClassesItem
import kotlinx.android.synthetic.main.lay_favorite_classes_item.view.*

class FavoriteClassesAdapter(private val context: Context) :
    RecyclerView.Adapter<FavoriteClassesAdapter.ViewHolder>() {
    private var alFavoriteClassesItem = ArrayList<PopularClassesItem>()
    var onFavoriteClassClick: ((item: PopularClassesItem) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvFavoriteClass = itemView.cvFavoriteClass as CardView
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
        context.loadImage(item.thumbnail, holder.ivClass)
        holder.tvTutorName.text = item.title
        holder.tvDescription.text = item.shortIntro
        holder.cvFavoriteClass.setOnClickListener {
            onFavoriteClassClick?.invoke(item)
        }
    }

    fun addData(alFavoriteCLassesItem: ArrayList<PopularClassesItem>) {
        this.alFavoriteClassesItem = alFavoriteCLassesItem
        notifyDataSetChanged()
    }
}