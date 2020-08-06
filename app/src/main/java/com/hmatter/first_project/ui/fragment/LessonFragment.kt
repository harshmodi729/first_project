package com.hmatter.first_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.LessonsAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.model.PopularClassesItem
import com.hmatter.first_project.ui.activity.VideoActivity
import kotlinx.android.synthetic.main.fragment_lesson.*

class LessonFragment(private val classesItem: PopularClassesItem) :
    BaseFragment(R.layout.fragment_lesson) {

    private lateinit var adapter: LessonsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LessonsAdapter(mContext)
        rvLessons.adapter = adapter
        adapter.addData(classesItem.videos)

        adapter.onVideoClick = { item ->
            mContext.startActivity(
                Intent(
                    mContext,
                    VideoActivity::class.java
                ).putExtra(Constants.VIDEO_ITEM, item)
            )
        }
    }
}