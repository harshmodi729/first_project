package com.ss_eduhub.edupi.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.jaeger.library.StatusBarUtil
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.extension.getProgressDialog
import com.ss_eduhub.edupi.extension.setBlurImage

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: AlertDialog? = null
    private var successDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarUtil.setTransparent(this)
        progressDialog = getProgressDialog(R.layout.lay_dialog_progress)
        successDialog = getProgressDialog(
            R.layout.lay_dialog_success,
            getString(R.string.account_created_successfully),
            Constants.SUCCESS_DIALOG
        )
    }

    fun showProgressDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!progressDialog?.isShowing!!) {
            setBlurImage(rootView, ivDialogBg)
            progressDialog?.show()
        }
    }

    fun hideProgressDialog(ivDialogBg: AppCompatImageView) {
        if (progressDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            progressDialog?.dismiss()
        }
    }

    fun showSuccessDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!successDialog?.isShowing!!) {
            setBlurImage(rootView, ivDialogBg)
            successDialog?.show()
        }
    }

    fun hideSuccessDialog(ivDialogBg: AppCompatImageView) {
        if (successDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            successDialog?.dismiss()
        }
    }
}