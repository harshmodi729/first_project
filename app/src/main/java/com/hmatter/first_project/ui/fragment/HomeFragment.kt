package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.HomeSliderAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.model.SliderItem
import kotlinx.android.synthetic.main.activity_intro.layDots
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.lay_toolbar.*
import kotlinx.coroutines.Runnable

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var homeSliderAdapter: HomeSliderAdapter
    private var homeSliderHandler = Handler()
    private val alSliderItem = ArrayList<SliderItem>()
    private var dots = ArrayList<TextView>()
    private val titles =
        arrayOf("Shonda Rhimes", "Gordon Ramsay", "Bear Grylls")
    private val details = arrayOf(
        "Shonda describes what fuels her passion.",
        "Mr. Ramsay teach how to make tastiest cakes.",
        "Bear Grylls shows how to survive in desert."
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnToolbarBack.visibility = View.GONE
        ivUserProfile.visibility = View.VISIBLE
        tvToolbarTitle.text = getString(R.string.home)

        homeSliderAdapter = HomeSliderAdapter(mContext, getSliderItem())
        homePager.adapter = homeSliderAdapter
        homePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                homeSliderHandler.removeCallbacks(homeSliderRunnable)
                homeSliderHandler.postDelayed(homeSliderRunnable, 3000)
                indicatorDots(position)
            }
        })
        dots = ArrayList()
        indicatorDots(0)
    }

    override fun onResume() {
        super.onResume()
        homeSliderHandler.postDelayed(homeSliderRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        homeSliderHandler.removeCallbacks(homeSliderRunnable)
    }

    val homeSliderRunnable = Runnable {
        if (homePager.currentItem == alSliderItem.size - 1)
            homePager.currentItem = 0
        else
            homePager.currentItem = homePager.currentItem + 1
    }

    private fun getSliderItem(): ArrayList<SliderItem> {
        for (i in 0..2) {
            val sliderItem = SliderItem()
            sliderItem.title = titles[i]
            sliderItem.detail = details[i]
            alSliderItem.add(sliderItem)
        }
        return alSliderItem
    }

    private fun indicatorDots(position: Int) {
        layDots.removeAllViews()
        for (i in 0 until alSliderItem.size) {
            val tvDot = TextView(mContext)
            dots.add(tvDot)
            dots[i].text = "\u25CF"
            dots[i].textSize = 10F
            dots[i].setTextColor(ContextCompat.getColor(mContext, R.color.colorInactiveIndicator))
            dots[i].setPadding(8, 8, 8, 8)
            layDots.addView(dots[i])
        }
        if (dots.size > 0) {
            dots[position].setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
        }
    }
}