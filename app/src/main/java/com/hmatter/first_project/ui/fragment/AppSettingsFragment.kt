package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.model.AppSettingsItem
import com.hmatter.first_project.viewmodel.AppSettingsViewModel
import kotlinx.android.synthetic.main.fragment_app_settings.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AppSettingsFragment : BaseFragment(R.layout.fragment_app_settings),
    View.OnClickListener {

    private var isDeleteCompleted = true
    private lateinit var appSettingsViewModel: AppSettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.app_setting)

        appSettingsViewModel = ViewModelProviders.of(this)[AppSettingsViewModel::class.java]
        appSettingsViewModel.getAppSettingsPreference(mContext)
        appSettingsViewModel.preferenceData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    setPreferenceData(it.item)
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        btnToolbarBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        swCellular.setOnCheckedChangeListener { _, isChecked ->
            appSettingsViewModel.setCellularData(
                mContext, isChecked
            )
        }
        btnStandardQuality.setOnClickListener(this)
        btnHighDefinition.setOnClickListener(this)
        btnDeleteCompleted.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnStandardQuality -> {
                appSettingsViewModel.setStandardVideoQuality(
                    mContext, true
                )
            }
            R.id.btnHighDefinition -> {
                appSettingsViewModel.setStandardVideoQuality(
                    mContext, false
                )
            }
            R.id.btnDeleteCompleted -> {
                isDeleteCompleted = !isDeleteCompleted
                appSettingsViewModel.setDeleteCompleted(
                    mContext, isDeleteCompleted
                )
            }
        }
    }

    private fun setPreferenceData(item: AppSettingsItem) {
        swCellular.isChecked = item.isCellularDataOn
        if (item.isStandardQuality) {
            btnStandardQuality.setImageResource(R.drawable.ic_check_checked)
            btnHighDefinition.setImageResource(R.drawable.ic_check_unchecked)
        } else {
            btnStandardQuality.setImageResource(R.drawable.ic_check_unchecked)
            btnHighDefinition.setImageResource(R.drawable.ic_check_checked)
        }

        isDeleteCompleted = item.isDeleteCompleted
        if (isDeleteCompleted)
            btnDeleteCompleted.setImageResource(R.drawable.ic_check_checked)
        else
            btnDeleteCompleted.setImageResource(R.drawable.ic_check_unchecked)
    }
}