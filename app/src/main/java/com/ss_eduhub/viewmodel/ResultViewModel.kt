package com.ss_eduhub.viewmodel

import android.content.Intent
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivityResultBinding
import com.ss_eduhub.model.TestListItem
import com.ss_eduhub.ui.activity.ResultActivity
import java.lang.ref.WeakReference

class ResultViewModel : BaseViewModel() {
    private lateinit var activity: WeakReference<ResultActivity>
    private lateinit var binding: ActivityResultBinding
    private lateinit var testListItem: TestListItem

    fun setBinding(
        activity: WeakReference<ResultActivity>,
        binding: ActivityResultBinding,
        intent: Intent
    ) {
        this.activity = activity
        this.binding = binding

        intent.extras?.let {
            testListItem = intent.getSerializableExtra(Constants.QUESTION_ITEM) as TestListItem
            "${testListItem.testName} - Result".also { binding.toolbar.tvToolbarTitle.text = it }
        } ?: kotlin.run {
            "Result".also { binding.toolbar.tvToolbarTitle.text = it }
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            activity.get()!!.onBackPressed()
        }
    }

}