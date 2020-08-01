package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import com.zerobranch.layout.SwipeLayout
import kotlinx.android.synthetic.main.lay_downloads_item.view.*

class DownloadAdapter(private val context: Context, private val isEnabledSwipe: Boolean = true) :
    RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {

    private var alDownloadItem = ArrayList<String>()
    var onDeleteClickListener: ((item: String, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.lay_downloads_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.swipeLayout.close()
        holder.swipeLayout.isEnabledSwipe = isEnabledSwipe
        holder.tvClassName.text = alDownloadItem[position]
        holder.deleteView.setOnClickListener {
            onDeleteClickListener?.invoke(alDownloadItem[position], position)
        }
    }

    override fun getItemCount() = alDownloadItem.size

    fun addData(alDownloadItem: ArrayList<String>) {
        this.alDownloadItem = alDownloadItem
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        alDownloadItem.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout: SwipeLayout = itemView.swipeLayout
        val tvClassName: AppCompatTextView = itemView.tvClassName
        val deleteView: ViewGroup = itemView.deleteView
    }
}
