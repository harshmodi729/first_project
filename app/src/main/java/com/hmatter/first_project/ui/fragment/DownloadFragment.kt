package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import kotlinx.android.synthetic.main.lay_toolbar.*

class DownloadFragment : BaseFragment(R.layout.fragment_download) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.download)
    }
}