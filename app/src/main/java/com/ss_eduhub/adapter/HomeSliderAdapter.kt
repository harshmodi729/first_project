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
import com.ss_eduhub.model.SliderItem
import kotlinx.android.synthetic.main.lay_home_slider_item.view.*

class HomeSliderAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<HomeSliderAdapter.ViewHolder>() {

    private var alSliderItem = ArrayList<SliderItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_home_slider_item, parent, false
        )
    )

    override fun getItemCount() = alSliderItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context.loadImage(alSliderItem[position].imagePath, holder.ivHomeSlider)
        holder.tvHomeSliderTitle.text = alSliderItem[position].title
        holder.tvHomeSliderDetail.text = alSliderItem[position].detail
    }

    fun addData(alSliderItem: ArrayList<SliderItem>) {
        this.alSliderItem = alSliderItem
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHomeSlider: AppCompatImageView = view.ivHomeSlider
        val tvHomeSliderTitle: AppCompatTextView = view.tvHomeSliderTitle
        val tvHomeSliderDetail: AppCompatTextView = view.tvHomeSliderDetail
    }
}