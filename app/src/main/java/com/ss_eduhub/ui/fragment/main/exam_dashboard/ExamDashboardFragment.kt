package com.ss_eduhub.ui.fragment.main.exam_dashboard

import android.os.Bundle
import android.view.View
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseFragment
import kotlinx.android.synthetic.main.lay_toolbar.*

class ExamDashboardFragment : BaseFragment(R.layout.fragment_exam_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = "Dashboard"
    }
}