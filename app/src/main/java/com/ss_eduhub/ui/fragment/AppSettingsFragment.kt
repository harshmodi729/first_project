package com.ss_eduhub.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.PreferenceConstants
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.extension.onDialogButtonClick
import com.ss_eduhub.viewmodel.AppSettingsViewModel
import kotlinx.android.synthetic.main.fragment_app_settings.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AppSettingsFragment : BaseFragment(R.layout.fragment_app_settings),
    View.OnClickListener {

    private var isCellularDataOn = false
    private var isStandardVideoQuality = true
    private var isDeleteCompleted = true
    private lateinit var appSettingsViewModel: AppSettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.app_setting)

        appSettingsViewModel = ViewModelProviders.of(this)[AppSettingsViewModel::class.java]
        appSettingsViewModel.getAppSettingsPreference(mContext)
        appSettingsViewModel.preferenceLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    isCellularDataOn = it.item.isCellularDataOn
                    isStandardVideoQuality = it.item.isStandardVideoQuality
                    isDeleteCompleted = it.item.isDeleteCompleted
                    setInitData()
                }
                is BaseResult.Error -> {
                    mContext.makeToastForServerError(it)
                }
            }
        })
        btnToolbarBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        swCellular.setOnCheckedChangeListener { _, isChecked ->
            isCellularDataOn = isChecked
            appSettingsViewModel.setBoolean(
                mContext, PreferenceConstants.IS_CELLULAR_DATA_ON, isCellularDataOn
            )
        }
        btnStandardQuality.setOnClickListener(this)
        btnHighDefinition.setOnClickListener(this)
        btnDeleteCompleted.setOnClickListener(this)
        btnDeleteAll.setOnClickListener(this)

        onDialogButtonClick = { _ ->
            hideEmptyDownloadDialog(ivDialogBg)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnStandardQuality -> {
                isStandardVideoQuality = true
                appSettingsViewModel.setBoolean(
                    mContext, PreferenceConstants.IS_STANDARD_VIDEO_QUALITY, isStandardVideoQuality
                )
                setInitData()
            }
            R.id.btnHighDefinition -> {
                isStandardVideoQuality = false
                appSettingsViewModel.setBoolean(
                    mContext, PreferenceConstants.IS_STANDARD_VIDEO_QUALITY, isStandardVideoQuality
                )
                setInitData()
            }
            R.id.btnDeleteCompleted -> {
                isDeleteCompleted = !isDeleteCompleted
                appSettingsViewModel.setBoolean(
                    mContext, PreferenceConstants.IS_DELETE_COMPLETED, isDeleteCompleted
                )
                setInitData()
            }
            R.id.btnDeleteAll -> {
                showEmptyDownloadDialog(layAppSettings, ivDialogBg)
            }
        }
    }

    /**
     * Method will set initial values of selection views.
     */
    private fun setInitData() {
        swCellular.isChecked = isCellularDataOn
        if (isStandardVideoQuality) {
            btnStandardQuality.setImageResource(R.drawable.ic_check_checked)
            btnHighDefinition.setImageResource(R.drawable.ic_check_unchecked)
        } else {
            btnStandardQuality.setImageResource(R.drawable.ic_check_unchecked)
            btnHighDefinition.setImageResource(R.drawable.ic_check_checked)
        }

        if (isDeleteCompleted)
            btnDeleteCompleted.setImageResource(R.drawable.ic_check_checked)
        else
            btnDeleteCompleted.setImageResource(R.drawable.ic_check_unchecked)
    }
}