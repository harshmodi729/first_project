package com.ss_eduhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.R
import com.ss_eduhub.extension.loadImage
import com.ss_eduhub.model.VideosItem
import kotlinx.android.synthetic.main.lay_class_lessons_item.view.*

class LessonsAdapter(private val context: Context) :
    RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

    private var alVideo = ArrayList<VideosItem>()
    var onVideoClick: ((item: VideosItem) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivVideoThumbnail: AppCompatImageView = itemView.ivVideoThumbnail
        val tvLessonsName: AppCompatTextView = itemView.tvLessonsName
        val tvClassDetail: AppCompatTextView = itemView.tvClassDetail
        val layLessonVideo: ViewGroup = itemView.layLessonVideo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.lay_class_lessons_item, parent, false)
        )
    }

    override fun getItemCount() = alVideo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alVideo[position]
        context.loadImage(item.thumbnail, holder.ivVideoThumbnail)
        holder.tvLessonsName.text = String.format("%02d", position + 1).plus(". ${item.videoTitle}")
        holder.tvClassDetail.text = item.videoIntro
        holder.layLessonVideo.setOnClickListener {
            onVideoClick?.invoke(item)
        }
    }

    fun addData(alVideo: ArrayList<VideosItem>) {
        this.alVideo = alVideo
        notifyDataSetChanged()
    }
}