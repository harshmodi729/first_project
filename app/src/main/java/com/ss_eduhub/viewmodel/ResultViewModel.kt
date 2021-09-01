package com.ss_eduhub.viewmodel

import android.content.Intent
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivityResultBinding
import com.ss_eduhub.model.QuestionItem
import com.ss_eduhub.model.TestListItem
import com.ss_eduhub.ui.activity.ResultActivity
import java.lang.ref.WeakReference

class ResultViewModel : BaseViewModel() {
    private lateinit var activity: WeakReference<ResultActivity>
    private lateinit var binding: ActivityResultBinding
    private lateinit var testListItem: TestListItem
    private var questionList = ArrayList<QuestionItem>()

    fun setBinding(
        activity: WeakReference<ResultActivity>,
        binding: ActivityResultBinding,
        intent: Intent
    ) {
        this.activity = activity
        this.binding = binding

        intent.extras?.let {
            testListItem = intent.getSerializableExtra(Constants.QUESTION_ITEM) as TestListItem
            questionList =
                intent.getSerializableExtra(Constants.QUESTION_LIST) as ArrayList<QuestionItem>
            "${testListItem.testName} - Result".also { binding.toolbar.tvToolbarTitle.text = it }
        } ?: kotlin.run {
            "Result".also { binding.toolbar.tvToolbarTitle.text = it }
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            activity.get()!!.onBackPressed()
        }

        var totalAttempt = 0
        var correct = 0
        var wrong = 0
        if (questionList.isNotEmpty()) {
            questionList.forEach {
                if (it.isAttempted) {
                    totalAttempt++
                    if (it.isAnswered)
                        correct++
                    else wrong++
                } else wrong++
            }
            binding.tvTotalQue.text = questionList.size.toString()
            binding.tvTotalAttempt.text = totalAttempt.toString()
            binding.tvCorrect.text = correct.toString()
            binding.tvWrong.text = wrong.toString()
            binding.tvMarks.text = correct.toString()
        }
    }

}