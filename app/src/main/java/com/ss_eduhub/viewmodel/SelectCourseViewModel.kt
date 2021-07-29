package com.ss_eduhub.viewmodel

import com.ss_eduhub.adapter.SelectCourseAdapter
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.databinding.ActivitySelectCourseBinding
import com.ss_eduhub.model.CourserItem
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

    private fun getList(): ArrayList<CourserItem> {
        val list = ArrayList<CourserItem>()

        list.add(CourserItem(1, "JEE Main"))
        list.add(CourserItem(2, "NEET"))
        list.add(CourserItem(3, "MH-CET"))
        list.add(CourserItem(4, "CAT"))
        return list
    }

    override fun onDashboardCardClick(item: CourserItem, position: Int) {

    }
}