package com.ss_eduhub.edupi.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.adapter.LessonsAdapter
import com.ss_eduhub.edupi.base.BaseFragment
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.model.PopularClassesItem
import com.ss_eduhub.edupi.ui.activity.VideoActivity
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