package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.extension.onDialogButtonClick
import kotlinx.android.synthetic.main.fragment_o_t_p.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class OTPFragment : BaseFragment(R.layout.fragment_o_t_p) {
    private lateinit var successDialog: AlertDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvToolbarTitle.text = getString(R.string.otp_header_text)
        val backButtonCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        activity?.onBackPressedDispatcher!!.addCallback(viewLifecycleOwner, backButtonCallback)
        btnToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        otpResend.setOnClickListener {
            mContext.makeToast("Otp resend request successfully.")
        }
        otp.setOtpCompletionListener {
            showSuccessDialog(layOtp, ivDialogBg)
        }
        onDialogButtonClick = {
            hideSuccessDialog(ivDialogBg)
            findNavController().navigate(R.id.action_nav_otp_to_nav_sign_in)
        }
    }
}