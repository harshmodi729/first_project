package com.ss_eduhub.edupi.ui.fragment.startup

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseFragment
import com.ss_eduhub.edupi.extension.bold
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.extension.onDialogButtonClick
import kotlinx.android.synthetic.main.fragment_o_t_p.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class OTPFragment : BaseFragment(R.layout.fragment_o_t_p) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvToolbarTitle.text = getString(R.string.otp_header_text)
        arguments?.let {
            val phoneNumber = OTPFragmentArgs.fromBundle(it).phoneNumber
            val description =
                String.format(mContext.getString(R.string.otp_heading_text), phoneNumber)
            tvOtpDescription.text = description.bold(phoneNumber)
        }
        val backButtonCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_nav_otp_to_nav_sign_up)
                }
            }
        activity?.onBackPressedDispatcher!!.addCallback(viewLifecycleOwner, backButtonCallback)
        btnToolbarBack.setOnClickListener {
            findNavController().navigate(R.id.action_nav_otp_to_nav_sign_up)
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