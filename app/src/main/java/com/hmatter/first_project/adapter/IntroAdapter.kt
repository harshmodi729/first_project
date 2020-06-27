package com.hmatter.first_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import com.hmatter.first_project.model.IntroItem
import kotlinx.android.synthetic.main.lay_intro_item.view.*

class IntroAdapter(private val context: Context, private val alIntroItem: ArrayList<IntroItem>) :
    RecyclerView.Adapter<IntroAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_intro_item, parent, false
        )
    )

    override fun getItemCount() = alIntroItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = alIntroItem[position]
        holder.tvIntroTitle.text = item.title
        holder.ivIntro.setBackgroundResource(R.drawable.slider_image)
        holder.tvIntroDetail.text = item.detail
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIntroTitle: AppCompatTextView = view.tvIntroTitle
        val ivIntro: AppCompatImageView = view.ivIntro
        val tvIntroDetail: AppCompatTextView = view.tvIntroDetail
    }
}