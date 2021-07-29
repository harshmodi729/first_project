package com.ss_eduhub.viewmodel

import android.view.View
import androidx.core.content.ContextCompat
import com.ss_eduhub.R
import com.ss_eduhub.adapter.ExamDashboardAdapter
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.databinding.FragmentExamDashboardBinding
import com.ss_eduhub.model.ExamDashboardItem
import com.ss_eduhub.ui.fragment.main.exam_dashboard.ExamDashboardFragment

class ExamDashboardViewModel : BaseViewModel(), ExamDashboardAdapter.OnCardClickListener {

    private lateinit var fragment: ExamDashboardFragment
    private lateinit var binding: FragmentExamDashboardBinding

    fun setBinding(fragment: ExamDashboardFragment, binding: FragmentExamDashboardBinding) {
        this.fragment = fragment
        this.binding = binding

        binding.toolbar.btnToolbarBack.visibility = View.GONE
        binding.toolbar.tvToolbarTitle.text = "Dashboard"

        val adapter = ExamDashboardAdapter(this)
        binding.rvExamDashboard.adapter = adapter
        adapter.addData(getList())
    }

    private fun getList(): ArrayList<ExamDashboardItem> {
        val list = ArrayList<ExamDashboardItem>()
        list.add(
            ExamDashboardItem(
                "Free Mock Test and Past Papers",
                "It gives you the best exposure of the official online entrance exam.",
                ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        list.add(
            ExamDashboardItem(
                "My Courses",
                "Select the course you want to pursue",
                ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        list.add(
            ExamDashboardItem(
                "Results(Theory Tests)",
                "It display all the results of attempted test at our offline centers.",
                ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        list.add(
            ExamDashboardItem(
                "Notifications",
                "It updates you with all the information for upcoming events exams etc.",
                ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_mock_test)!!
            )
        )
        return list
    }

    override fun onDashboardCardClick(item: ExamDashboardItem, position: Int) {

    }
}