package com.ss_eduhub.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.adapter.SelectCourseAdapter
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivitySelectCourseBinding
import com.ss_eduhub.model.CourseItem
import com.ss_eduhub.ui.activity.CourseTestActivity
import com.ss_eduhub.ui.activity.SelectCourseActivity
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SelectCourseViewModel : BaseViewModel(), SelectCourseAdapter.OnCardClickListener {

    private lateinit var activity: WeakReference<SelectCourseActivity>
    private lateinit var binding: ActivitySelectCourseBinding
    val alCourseItem = MutableLiveData<BaseResult<ArrayList<CourseItem>>>()


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
        adapter.addData(alCourseItem)
    }

    fun getCourseList() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getCoursesList()
                if (response.success) {
                    response.data?.let {
                        alCourseItem.value = BaseResult.Success(it)
                    } ?: kotlin.run {
                        alCourseItem.value =
                            BaseResult.Error(IllegalStateException("Oops something went wrong."))
                    }
                } else {
                    alCourseItem.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                alCourseItem.value = BaseResult.Error(exception)
            }
        }
    }

//    private fun getList(): ArrayList<CourseItem> {
//        val list = ArrayList<CourseItem>()
//
//        list.add(CourseItem(1, "JEE Main"))
//        list.add(CourseItem(2, "NEET"))
//        list.add(CourseItem(3, "MH-CET"))
//        list.add(CourseItem(4, "CAT"))
//        return list
//    }

    override fun onCourseSelected(item: CourseItem) {
        activity.get()!!.startActivity(
            Intent(activity.get()!!, CourseTestActivity::class.java).putExtra(
                Constants.COURSE_ITEM,
                item
            )
        )
    }
}