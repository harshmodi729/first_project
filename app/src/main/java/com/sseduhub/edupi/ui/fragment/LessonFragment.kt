package com.sseduhub.edupi.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sseduhub.edupi.R
import com.sseduhub.edupi.adapter.LessonsAdapter
import com.sseduhub.edupi.base.BaseFragment
import com.sseduhub.edupi.common.Constants
import com.sseduhub.edupi.model.PopularClassesItem
import com.sseduhub.edupi.ui.activity.VideoActivity
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