package com.ss_eduhub.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.databinding.ActivityCourseTestBinding
import com.ss_eduhub.viewmodel.CourseTestViewModel
import java.lang.ref.WeakReference

class CourseTestActivity : BaseActivity() {
    private lateinit var binding: ActivityCourseTestBinding
    private lateinit var viewModel: CourseTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this)[CourseTestViewModel::class.java]
        viewModel.setBinding(WeakReference(this), binding, intent)
    }
}