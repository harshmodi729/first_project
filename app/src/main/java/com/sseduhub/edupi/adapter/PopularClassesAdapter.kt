package com.sseduhub.edupi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sseduhub.edupi.R
import com.sseduhub.edupi.extension.isBlankOrEmpty
import com.sseduhub.edupi.extension.loadImage
import com.sseduhub.edupi.model.PopularClassesItem
import kotlinx.android.synthetic.main.lay_popular_classes_item.view.*

class PopularClassesAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<PopularClassesAdapter.ViewHolder>() {

    private var alPopularClassItem = ArrayList<PopularClassesItem>()
    var onCardClickListener: ((item: PopularClassesItem, position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_popular_classes_item, parent, false
        )
    )

    override fun getItemCount() = alPopularClassItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alPopularClassItem[position]
        context.loadImage(item.thumbnail, holder.ivClass)
        holder.tvClassName.text = item.title
        if (item.price.isBlankOrEmpty()) {
            holder.tvPrice.text = context.getString(R.string.rupee_symbol).plus("0.00")
        } else {
            holder.tvPrice.text = context.getString(R.string.rupee_symbol).plus(item.price)
        }
        holder.tvClassDetail.text = item.shortIntro
        holder.tvClassTags.text = item.catName
        holder.ratingClass.rating = item.ratings.toFloat()
        holder.tvTotalVideos.text = "${item.videosCount}".plus(" Videos")
        holder.layClass.setOnClickListener {
            onCardClickListener?.invoke(item, position)
        }
    }

    fun addData(alPopularClassItem: ArrayList<PopularClassesItem>) {
        this.alPopularClassItem = alPopularClassItem
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivClass: AppCompatImageView = view.ivClass
        val tvClassName: AppCompatTextView = view.tvClassName
        val tvPrice: AppCompatTextView = view.tvPrice
        val tvClassDetail: AppCompatTextView = view.tvClassDetail
        val tvClassTags: AppCompatTextView = view.tvClassTags
        val ratingClass: RatingBar = view.ratingClass
        val tvTotalVideos: AppCompatTextView = view.tvTotalVideos
        val layClass: CardView = itemView.layClass
    }
}