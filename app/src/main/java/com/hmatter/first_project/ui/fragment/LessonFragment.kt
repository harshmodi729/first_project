package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.LessonsAdapter
import com.hmatter.first_project.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_lesson.*

class LessonFragment : BaseFragment(R.layout.fragment_lesson) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLessons.adapter = LessonsAdapter(mContext)
    }
}