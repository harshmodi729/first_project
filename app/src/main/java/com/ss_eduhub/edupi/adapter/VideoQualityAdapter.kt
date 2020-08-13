package com.ss_eduhub.edupi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.model.VideoQualityItem
import kotlinx.android.synthetic.main.lay_video_quality_item.view.*


class VideoQualityAdapter(private val context: Context) :
    RecyclerView.Adapter<VideoQualityAdapter.ViewHolder>() {

    private var checkedPosition = -1
    private var alVideoQualityItem = ArrayList<VideoQualityItem>()
    var onVideoQualityChecked: ((selectorOverride: List<DefaultTrackSelector.SelectionOverride>) -> Unit)? =
        null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTrackName = itemView.tvTrackName as AppCompatTextView
        val ivVideoQuality = itemView.ivVideoQuality as AppCompatImageView
        val layVideoQuality = itemView.layVideoQuality as ViewGroup
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.lay_dialog_video_quality, parent, false)
        )
    }

    override fun getItemCount() = alVideoQualityItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alVideoQualityItem[position]

        holder.tvTrackName.text = item.trackName
        if (checkedPosition == -1) {
            holder.ivVideoQuality.setImageResource(R.drawable.ic_check_unchecked)
        } else {
            if (checkedPosition == position) {
                holder.ivVideoQuality.setImageResource(R.drawable.ic_check_checked)
            } else {
                holder.ivVideoQuality.setImageResource(R.drawable.ic_check_unchecked)
            }
        }

        holder.layVideoQuality.setOnClickListener(View.OnClickListener {
            holder.ivVideoQuality.setImageResource(R.drawable.ic_check_checked)
            if (checkedPosition != position) {
                notifyItemChanged(checkedPosition)
                checkedPosition = position
                onVideoQualityChecked?.invoke(item.selectionOverride!!)
            }
        })
    }

    fun addData(alVideoQualityItem: ArrayList<VideoQualityItem>) {
        this.alVideoQualityItem = alVideoQualityItem
        notifyDataSetChanged()
    }
}