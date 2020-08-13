package com.sseduhub.edupi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sseduhub.edupi.R
import com.sseduhub.edupi.model.SliderItem
import kotlinx.android.synthetic.main.lay_home_slider_item.view.*

class HomeSliderAdapter(
    private val context: Context,
    private val alSliderItem: ArrayList<SliderItem>
) :
    RecyclerView.Adapter<HomeSliderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.lay_home_slider_item, parent, false
        )
    )

    override fun getItemCount() = alSliderItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = context.resources.getIdentifier(
            "slider_".plus(position + 1),
            "drawable",
            context.packageName
        )
        holder.ivHomeSlider.setBackgroundResource(id)
        holder.tvHomeSliderTitle.text = alSliderItem[position].title
        holder.tvHomeSliderDetail.text = alSliderItem[position].detail
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivHomeSlider: AppCompatImageView = view.ivHomeSlider
        val tvHomeSliderTitle: AppCompatTextView = view.tvHomeSliderTitle
        val tvHomeSliderDetail: AppCompatTextView = view.tvHomeSliderDetail
    }
}