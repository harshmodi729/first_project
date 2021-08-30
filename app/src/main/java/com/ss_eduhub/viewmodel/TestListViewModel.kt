package com.ss_eduhub.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ss_eduhub.R
import com.ss_eduhub.adapter.TestListAdapter
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivityTestListBinding
import com.ss_eduhub.extension.hideKeyboard
import com.ss_eduhub.model.CourseTestItem
import com.ss_eduhub.model.TestListItem
import com.ss_eduhub.ui.activity.QuestionActivity
import com.ss_eduhub.ui.activity.TestListActivity
import java.lang.ref.WeakReference

class TestListViewModel : BaseViewModel(), TestListAdapter.OnCardClickListener {

    private lateinit var activity: WeakReference<TestListActivity>
    private lateinit var binding: ActivityTestListBinding
    private var courseTestItem: CourseTestItem? = null

    @SuppressLint("SetTextI18n")
    fun setBinding(
        activity: WeakReference<TestListActivity>,
        binding: ActivityTestListBinding,
        intent: Intent
    ) {
        this.activity = activity
        this.binding = binding

        intent.extras?.let {
            courseTestItem =
                intent.getSerializableExtra(Constants.COURSE_TEST_ITEM) as CourseTestItem
            binding.toolbar.tvToolbarTitle.text =
                "${intent.getStringExtra("prefix")} - ${courseTestItem!!.paper}"
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
        val listAdapter = TestListAdapter(this)
        binding.rvTest.adapter = listAdapter
        listAdapter.addData(getData())
    }

    private fun getData(): ArrayList<TestListItem> {
        val list = ArrayList<TestListItem>()
        list.add(TestListItem(1, "NLM(Without friction) Practice Set", true))
        list.add(TestListItem(2, "Units, Dimension and Analysis Practice Set", false))
        list.add(TestListItem(3, "Relative Motion Practice Set", false))
        list.add(TestListItem(4, "2 D Motion Practice Set", false))
        list.add(TestListItem(5, "1 D Motion Practice Set", true))
        return list
    }

    override fun onCourseSelected(item: TestListItem) {
        activity.get()!!.startActivity(
            Intent(
                activity.get()!!,
                QuestionActivity::class.java
            ).putExtra(Constants.QUESTION_ITEM, item)
                .putExtra(Constants.COURSE_TEST_ITEM, courseTestItem)
        )
    }
}