package com.ss_eduhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.R
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.loadImage
import com.ss_eduhub.extension.ratingFormat
import com.ss_eduhub.model.CommentItem
import kotlinx.android.synthetic.main.lay_comment_item.view.*
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentAdapter(private val context: Context) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var alComments = ArrayList<CommentItem>()
    var onEditClick: ((commentItem: CommentItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lay_comment_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alComments[position]
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val date = sdf.parse(item.created)
        holder.tvUserName.text = item.userName
        context.loadImage(item.profile, holder.ivCommentedUser)
        holder.ratingClass.rating = item.rating.ratingFormat()
        holder.btnEditComment.visibility = View.GONE
        if (item.userId == Constants.userProfileData.id)
            holder.btnEditComment.visibility = View.VISIBLE
        holder.btnEditComment.setOnClickListener {
            onEditClick?.invoke(item)
        }
        holder.tvCommentDateTime.text =
            SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(date!!)
        holder.tvComment.text = alComments[position].comments
    }

    override fun getItemCount() = alComments.size

    fun addData(alComments: ArrayList<CommentItem>) {
        this.alComments = alComments
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: AppCompatTextView = itemView.tvUserName
        val ratingClass: MaterialRatingBar = itemView.ratingClass
        val btnEditComment: AppCompatImageButton = itemView.btnEditComment
        val tvComment: AppCompatTextView = itemView.tvComment
        val tvCommentDateTime: AppCompatTextView = itemView.tvCommentDateTime
        val ivCommentedUser: AppCompatImageView = itemView.ivCommentedUser
    }
}
