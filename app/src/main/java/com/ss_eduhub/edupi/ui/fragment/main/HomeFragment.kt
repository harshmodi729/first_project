package com.ss_eduhub.edupi.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.adapter.HomeSliderAdapter
import com.ss_eduhub.edupi.adapter.PopularClassesAdapter
import com.ss_eduhub.edupi.base.BaseFragment
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.extension.isBlankOrEmpty
import com.ss_eduhub.edupi.extension.loadImage
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.model.SliderItem
import com.ss_eduhub.edupi.ui.activity.ClassActivity
import com.ss_eduhub.edupi.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_intro.layDots
import kotlinx.android.synthetic.main.lay_no_data.*
import kotlinx.android.synthetic.main.lay_toolbar.*
import kotlinx.coroutines.Runnable

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var homeSliderAdapter: HomeSliderAdapter
    private lateinit var popularClassesAdapter: PopularClassesAdapter
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

        popularClassesAdapter = PopularClassesAdapter(mContext)
        rvPopularClasses.layoutManager = LinearLayoutManager(mContext)
        rvPopularClasses.adapter = popularClassesAdapter
        rvPopularClasses.isNestedScrollingEnabled = false

        if (!Constants.userProfileData.profile.isBlankOrEmpty()) {
            mContext.loadImage(Constants.userProfileData.profile, ivUserProfile)
        }
        val homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeViewModel.getPopularClasses().apply {
            layHome.post {
                showProgressDialog(layHome, ivDialogBg)
            }
        }
        homeViewModel.popularClassesLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    rvPopularClasses.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE
                    popularClassesAdapter.addData(it.item)
                }
                is BaseResult.Error -> {
                    rvPopularClasses.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                    mContext.makeToast(it.errorMessage)
                }
            }
        })

        ivUserProfile.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_nav_home_to_nav_user_profile)
        }
        popularClassesAdapter.onCardClickListener = { item, _ ->
            mContext.startActivity(
                Intent(mContext, ClassActivity::class.java).putExtra(Constants.CLASS_ITEM, item)
            )
        }
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
        alSliderItem.clear()
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
            dots[i].text = getString(R.string.indicator_bullet)
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