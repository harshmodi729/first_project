package com.hmatter.first_project.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.CommentAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.extension.loadImage
import com.hmatter.first_project.model.PopularClassesItem
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment(private val classesItem: PopularClassesItem) :
    BaseFragment(R.layout.fragment_overview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvClassOverview.text =
                HtmlCompat.fromHtml(classesItem.description, 0)
        } else {
            tvClassOverview.text = Html.fromHtml(classesItem.description)
        }
        tvTotalVideos.text = String.format("%02d", classesItem.videosCount).plus(" video lessons")

        mContext.loadImage(Constants.userProfileData.profile, ivUserProfile)
        val adapter = CommentAdapter(mContext)
        rvComments.adapter = adapter
        adapter.addData(classesItem.comments)
    }
}