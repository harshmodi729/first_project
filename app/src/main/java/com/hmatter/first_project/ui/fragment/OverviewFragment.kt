package com.hmatter.first_project.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.model.PopularClassesItem
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment(private val classesItem: PopularClassesItem) :
    BaseFragment(R.layout.fragment_overview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvClassOverview.text =
                Html.fromHtml(classesItem.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            tvClassOverview.text = Html.fromHtml(classesItem.description)
        }
    }
}