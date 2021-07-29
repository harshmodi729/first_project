package com.ss_eduhub.ui.fragment.main.exam_dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.ss_eduhub.R
import com.ss_eduhub.adapter.ExamDashboardAdapter
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.model.ExamDashboardItem
import com.ss_eduhub.ui.activity.SelectCourseActivity
import kotlinx.android.synthetic.main.fragment_exam_dashboard.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class ExamDashboardFragment : BaseFragment(R.layout.fragment_exam_dashboard),
    ExamDashboardAdapter.OnCardClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = "Dashboard"

        val list = ArrayList<ExamDashboardItem>()
        list.add(
            ExamDashboardItem(
                "Free Mock Test and Past Papers",
                "It gives you the best exposure of the official online entrance exam.",
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        list.add(
            ExamDashboardItem(
                "My Courses",
                "Select the course you want to pursue",
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        list.add(
            ExamDashboardItem(
                "Results(Theory Tests)",
                "It display all the results of attempted test at our offline centers.",
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        list.add(
            ExamDashboardItem(
                "Notifications",
                "It updates you with all the information for upcoming events exams etc.",
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        val adapter = ExamDashboardAdapter(this)
        rvExamDashboard.adapter = adapter
        adapter.addData(list)
    }

    override fun onDashboardCardClick(item: ExamDashboardItem, position: Int) {
        if (position == 1)
            mContext.startActivity(Intent(mContext, SelectCourseActivity::class.java))
    }
}