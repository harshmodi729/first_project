package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_app_settings.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AppSettingsFragment : BaseFragment(R.layout.fragment_app_settings),
    View.OnClickListener {

    private var isCellularData = false
    private var isStandardQuality = true
    private var isDeleteCompleted = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.app_setting)

        btnToolbarBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        setPreferenceData()
        swCellular.setOnCheckedChangeListener { _, isChecked ->
            isCellularData = isChecked
        }
        btnStandardQuality.setOnClickListener(this)
        btnHighDefinition.setOnClickListener(this)
        btnDeleteCompleted.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnStandardQuality -> {
                isStandardQuality = true
                setPreferenceData()
            }
            R.id.btnHighDefinition -> {
                isStandardQuality = false
                setPreferenceData()
            }
            R.id.btnDeleteCompleted -> {
                isDeleteCompleted = !isDeleteCompleted
                setPreferenceData()
            }
        }
    }

    private fun setPreferenceData() {
        swCellular.isChecked = isCellularData

        if (isStandardQuality) {
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