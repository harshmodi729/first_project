package com.ss_eduhub.viewmodel

import android.content.Intent
import com.ss_eduhub.adapter.SelectCourseAdapter
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivitySelectCourseBinding
import com.ss_eduhub.model.CourseItem
import com.ss_eduhub.ui.activity.CourseTestActivity
import com.ss_eduhub.ui.activity.SelectCourseActivity
import java.lang.ref.WeakReference

class SelectCourseViewModel : BaseViewModel(), SelectCourseAdapter.OnCardClickListener {

    private lateinit var activity: WeakReference<SelectCourseActivity>
    private lateinit var binding: ActivitySelectCourseBinding

    fun setBinding(
        activity: WeakReference<SelectCourseActivity>,
        binding: ActivitySelectCourseBinding
    ) {
        this.activity = activity
        this.binding = binding

        binding.toolbar.tvToolbarTitle.text = "Select Course"
        binding.toolbar.btnToolbarBack.setOnClickListener {
            activity.get()!!.onBackPressed()
        }

        val adapter = SelectCourseAdapter(this)
        binding.rvCourse.adapter = adapter
        adapter.addData(getList())
    }

    private fun getList(): ArrayList<CourseItem> {
        val list = ArrayList<CourseItem>()

        list.add(CourseItem(1, "JEE Main"))
        list.add(CourseItem(2, "NEET"))
        list.add(CourseItem(3, "MH-CET"))
        list.add(CourseItem(4, "CAT"))
        return list
    }

    override fun onCourseSelected(item: CourseItem) {
        activity.get()!!.startActivity(
            Intent(activity.get()!!, CourseTestActivity::class.java).putExtra(
                Constants.COURSE_ITEM,
                item
            )
        )
    }
}