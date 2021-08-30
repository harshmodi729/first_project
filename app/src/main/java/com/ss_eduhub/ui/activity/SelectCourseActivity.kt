package com.ss_eduhub.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.databinding.ActivitySelectCourseBinding
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.model.CourseItem
import com.ss_eduhub.viewmodel.SelectCourseViewModel
import java.lang.ref.WeakReference

class SelectCourseActivity : BaseActivity() {

    private lateinit var binding: ActivitySelectCourseBinding
    private lateinit var viewModel: SelectCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SelectCourseViewModel::class.java]
        viewModel.setBinding(WeakReference(this), binding)
        viewModel.getCourseList().apply {
            binding.laySelectCourse.post {
                showProgressDialog(binding.laySelectCourse, binding.ivDialogBg)
            }
        }
        viewModel.getLiveData().observe(this, this::consumeCourseResponse)
    }

    private fun consumeCourseResponse(response: BaseResult<ArrayList<CourseItem>>) {
        hideProgressDialog(binding.ivDialogBg)
        when (response) {
            is BaseResult.Success -> {
            }
            is BaseResult.Error -> {
                makeToastForServerError(response)
            }
        }
    }
}