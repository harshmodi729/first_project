package com.ss_eduhub.edupi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.model.CommentItem
import kotlinx.android.synthetic.main.lay_comment_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentAdapter(private val context: Context) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var alComments = ArrayList<CommentItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lay_comment_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val date = sdf.parse(alComments[position].created)
        holder.tvCommentDateTime.text =
            SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.US).format(date!!)
        holder.tvComment.text = alComments[position].comments
    }

    override fun getItemCount() = alComments.size

    fun addData(alComments: ArrayList<CommentItem>) {
        this.alComments = alComments
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvComment: AppCompatTextView = itemView.tvComment
        val tvCommentDateTime: AppCompatTextView = itemView.tvCommentDateTime
        val ivCommentedUser: AppCompatImageView = itemView.ivCommentedUser
    }
}
