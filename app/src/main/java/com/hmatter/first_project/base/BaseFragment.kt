package com.hmatter.first_project.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment(layResourceId: Int) : Fragment(layResourceId) {

    lateinit var mContext: Activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = context as BaseActivity

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}