package com.ss_eduhub.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.databinding.ActivityCourseTestBinding
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.model.CourseTestItem
import com.ss_eduhub.viewmodel.CourseTestViewModel
import java.lang.ref.WeakReference

class CourseTestActivity : BaseActivity() {
    private lateinit var binding: ActivityCourseTestBinding
    private lateinit var viewModel: CourseTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CourseTestViewModel::class.java]
        viewModel.setBinding(WeakReference(this), binding, intent)
        viewModel.getPaperList().apply {
            binding.layCourseTest.post {
                showProgressDialog(binding.layCourseTest, binding.ivDialogBg)
            }
        }
        viewModel.getLiveData().observe(this, this::consumePaperResponse)
    }

    private fun consumePaperResponse(response: BaseResult<ArrayList<CourseTestItem>>) {
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