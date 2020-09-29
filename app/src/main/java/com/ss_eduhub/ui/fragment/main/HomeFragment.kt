package com.ss_eduhub.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ss_eduhub.R
import com.ss_eduhub.adapter.HomeCategoryAdapter
import com.ss_eduhub.adapter.HomeSliderAdapter
import com.ss_eduhub.adapter.PopularClassesAdapter
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.isBlankOrEmpty
import com.ss_eduhub.extension.loadImage
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.model.SliderItem
import com.ss_eduhub.ui.activity.ClassActivity
import com.ss_eduhub.viewmodel.HomeViewModel
import com.ss_eduhub.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_intro.layDots
import kotlinx.android.synthetic.main.lay_toolbar.*
import kotlinx.coroutines.Runnable

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var homeSliderAdapter: HomeSliderAdapter
    private lateinit var popularClassesAdapter: PopularClassesAdapter
    private lateinit var categoriesAdapter: HomeCategoryAdapter
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
    private lateinit var onViewMoreTagClickListener: OnViewMoreClickListener

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.home)

        homeSliderAdapter = HomeSliderAdapter(mContext)
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
        categoriesAdapter = HomeCategoryAdapter(mContext)

        rvPopularClasses.layoutManager = LinearLayoutManager(mContext)
        rvPopularClasses.adapter = popularClassesAdapter
        rvPopularClasses.isNestedScrollingEnabled = false

        rvCategories.adapter = categoriesAdapter
        rvCategories.isNestedScrollingEnabled = false

        if (!Constants.userProfileData.profile.isBlankOrEmpty()) {
            mContext.loadImage(Constants.userProfileData.profile, ivUserProfile)
        }
        val homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        val searchViewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
        layHome.post {
            showProgressDialog(layHome, ivDialogBg)
            homeViewModel.getBanners()
            homeViewModel.getPopularClasses()
            searchViewModel.getPopularCategoryList()
        }
        homeViewModel.bannersLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is BaseResult.Success -> {
                    homeSliderAdapter.addData(it.item)
                    if (it.item.isNotEmpty()) {
                        tvNewestClassesLabel.visibility = View.VISIBLE
                        layHomePager.visibility = View.VISIBLE
                    } else {
                        tvNewestClassesLabel.visibility = View.GONE
                        layHomePager.visibility = View.GONE
                    }
                }
                is BaseResult.Error -> {
                    tvNewestClassesLabel.visibility = View.GONE
                    layHomePager.visibility = View.GONE
                    mContext.makeToastForServerError(it)
                }
            }
        })
        homeViewModel.popularClassesLiveData.observe(viewLifecycleOwner, {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    popularClassesAdapter.addData(it.item)
                    if (it.item.isNotEmpty()) {
                        rvPopularClasses.visibility = View.VISIBLE
                        tvPopularClassesLabel.visibility = View.VISIBLE
                    } else {
                        rvPopularClasses.visibility = View.GONE
                        tvPopularClassesLabel.visibility = View.GONE
                    }
                }
                is BaseResult.Error -> {
                    rvPopularClasses.visibility = View.GONE
                    tvPopularClassesLabel.visibility = View.GONE
                    mContext.makeToastForServerError(it)
                }
            }
        })
        searchViewModel.alPopularCategoryItem.observe(
            viewLifecycleOwner,
            {
                hideProgressDialog(ivDialogBg)
                when (it) {
                    is BaseResult.Success -> {
                        categoriesAdapter.addData(it.item)
                        if (it.item.isNotEmpty()) {
                            rvCategories.visibility = View.VISIBLE
                            tvCategoriesLabel.visibility = View.VISIBLE
                            btnViewMore.visibility = View.VISIBLE
                        } else {
                            rvCategories.visibility = View.GONE
                            tvCategoriesLabel.visibility = View.GONE
                            btnViewMore.visibility = View.GONE
                        }
                    }
                    is BaseResult.Error -> {
                        mContext.makeToastForServerError(it)
                        tvCategoriesLabel.visibility = View.GONE
                        rvCategories.visibility = View.GONE
                        btnViewMore.visibility = View.GONE
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
        onViewMoreTagClickListener = mContext as OnViewMoreClickListener
        btnViewMore.setOnClickListener {
            onViewMoreTagClickListener.onViewMoreTagClick()
        }
        categoriesAdapter.onCategoryClick = {
            onViewMoreTagClickListener.onViewMoreTagClick()
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

    interface OnViewMoreClickListener {
        fun onViewMoreTagClick()
    }
}