package com.ss_eduhub.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.adapter.CourseTestAdapter
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivityCourseTestBinding
import com.ss_eduhub.model.CourseItem
import com.ss_eduhub.model.CourseTestItem
import com.ss_eduhub.ui.activity.CourseTestActivity
import com.ss_eduhub.ui.activity.TestListActivity
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class CourseTestViewModel : BaseViewModel(), CourseTestAdapter.OnCardClickListener {

    private lateinit var activity: WeakReference<CourseTestActivity>
    private lateinit var binding: ActivityCourseTestBinding
    private var courseItem: CourseItem? = null
    private var liveData = MutableLiveData<BaseResult<ArrayList<CourseTestItem>>>()
    private lateinit var adapter: CourseTestAdapter

    fun setBinding(
        activity: WeakReference<CourseTestActivity>,
        binding: ActivityCourseTestBinding,
        intent: Intent
    ) {
        this.activity = activity
        this.binding = binding

        intent.extras?.let {
            courseItem = intent.getSerializableExtra(Constants.COURSE_ITEM) as CourseItem
            binding.toolbar.tvToolbarTitle.text = courseItem!!.title
        } ?: kotlin.run {
            binding.toolbar.tvToolbarTitle.text = "Select Test"
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            activity.get()!!.onBackPressed()
        }

        adapter = CourseTestAdapter(this)
        binding.rvCourseTest.adapter = adapter
        getPaperList()
    }

    fun getLiveData(): MutableLiveData<BaseResult<ArrayList<CourseTestItem>>> {
        return liveData
    }

    fun getPaperList() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getPaperList(courseItem?.courseId!!)
                if (response.success) {
                    response.data?.let {
                        liveData.value = BaseResult.Success(it)
                        adapter.addData(it)
                    } ?: kotlin.run {
                        liveData.value =
                            BaseResult.Error(IllegalStateException("Oops something went wrong."))
                    }
                } else {
                    liveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                liveData.value = BaseResult.Error(exception)
            }
        }
    }

    /*private fun getList(): ArrayList<CourseTestItem> {
        val list = ArrayList<CourseTestItem>()

        list.add(CourseTestItem(1, "Question Bank"))
        list.add(CourseTestItem(2, "Unit Test"))
        list.add(CourseTestItem(3, "Group Test"))
        list.add(CourseTestItem(4, "Full Syllabus Test"))
        return list
    }*/

    override fun onCourseSelected(item: CourseTestItem) {
        activity.get()!!.startActivity(
            Intent(
                activity.get()!!,
                TestListActivity::class.java
            ).putExtra(Constants.COURSE_TEST_ITEM, item)
                .putExtra("prefix", binding.toolbar.tvToolbarTitle.text.toString())
        )
    }
}