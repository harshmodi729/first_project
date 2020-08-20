package com.ss_eduhub.ui.fragment.startup

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ss_eduhub.R
import com.ss_eduhub.adapter.IntroAdapter
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.extension.makeToast
import com.ss_eduhub.model.SliderItem
import com.ss_eduhub.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : BaseFragment(R.layout.fragment_intro) {
    private val alSliderItem = ArrayList<SliderItem>()
    private lateinit var introAdapter: IntroAdapter
    private var dots = ArrayList<TextView>()
    private val titles =
        arrayOf("Learn from the best", "Download and watch anytime", "Explore a range of topics")
    private val details = arrayOf(
        "Online classes taught by the world's best. Gordon Ramsey, Stephen Curry, and more.",
        "Download up to 10 digestible lessons that you can watch offline at any time",
        "Perfect homemade paste, or write a novel... All wit access to 100+ class."
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInViewModel =
            ViewModelProviders.of(this)[SignInViewModel::class.java]
        signInViewModel.visitIntroLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    findNavController().navigate(R.id.action_nav_intro_to_nav_sign_in)
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })

        introAdapter = IntroAdapter(mContext, getIntroItem())
        introPager.adapter = introAdapter
        introPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorDots(position)
            }
        })
        dots = ArrayList()
        indicatorDots(0)

        btnSkip.setOnClickListener {
            signInViewModel.setVisitIntro(mContext)
        }
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

    private fun getIntroItem(): ArrayList<SliderItem> {
        for (i in 0..2) {
            val introItem = SliderItem()
            introItem.title = titles[i]
            introItem.detail = details[i]
            alSliderItem.add(introItem)
        }
        return alSliderItem
    }
}
