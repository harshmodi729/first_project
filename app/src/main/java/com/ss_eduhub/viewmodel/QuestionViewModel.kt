package com.ss_eduhub.viewmodel

import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivityQuestionBinding
import com.ss_eduhub.extension.hideKeyboard
import com.ss_eduhub.model.TestListItem
import com.ss_eduhub.ui.activity.QuestionActivity
import java.lang.ref.WeakReference

class QuestionViewModel : BaseViewModel() {

    private lateinit var activity: WeakReference<QuestionActivity>
    private lateinit var binding: ActivityQuestionBinding
    private var testListItem: TestListItem? = null

    fun setBinding(
        activity: WeakReference<QuestionActivity>,
        binding: ActivityQuestionBinding,
        intent: Intent
    ) {
        this.activity = activity
        this.binding = binding

        intent.extras?.let {
            testListItem =
                intent.getSerializableExtra(Constants.QUESTION_ITEM) as TestListItem
            binding.toolbar.tvToolbarTitle.text = testListItem!!.testName
        } ?: kotlin.run {
            binding.toolbar.tvToolbarTitle.text = "Select Test"
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            activity.get()!!.onBackPressed()
        }

        binding.tvSubject.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                activity.get()!!,
                R.drawable.layout_bg_dropdown
            )
        )
        binding.tvSubject.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textView: TextView,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    activity.get()!!.hideKeyboard(binding.tvSubject)
                    return true
                }
                return false
            }
        })

        val data = arrayOf("Physics", "Chemistry", "Biology", "Maths", "Hindi")
        val adapter =
            activity.get()?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    data
                )
            }
        binding.tvSubject.setAdapter(adapter)
    }
}