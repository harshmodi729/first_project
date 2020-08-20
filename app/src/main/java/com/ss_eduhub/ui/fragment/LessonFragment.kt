package com.ss_eduhub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ss_eduhub.R
import com.ss_eduhub.adapter.LessonsAdapter
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.common.Constants
import com.ss_eduhub.model.PopularClassesItem
import com.ss_eduhub.ui.activity.VideoActivity
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