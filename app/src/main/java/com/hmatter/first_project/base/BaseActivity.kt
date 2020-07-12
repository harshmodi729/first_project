package com.hmatter.first_project.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.hmatter.first_project.R
import com.hmatter.first_project.extension.getProgressDialog
import com.hmatter.first_project.extension.setBlurImage
import com.jaeger.library.StatusBarUtil

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: AlertDialog? = null
    private var successDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarUtil.setTransparent(this)
        progressDialog = getProgressDialog(R.layout.lay_progress_dialog)
        successDialog = getProgressDialog(
            R.layout.lay_success_dialog,
            getString(R.string.account_created_successfully),
            true
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

    fun showSuccessDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!successDialog?.isShowing!!) {
            setBlurImage(rootView, ivDialogBg)
            successDialog?.show()
        }
    }

    fun hideProgressDialog(ivDialogBg: AppCompatImageView) {
        if (progressDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            progressDialog?.hide()
        }
    }

    fun hideSuccessDialog(ivDialogBg: AppCompatImageView) {
        if (successDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            successDialog?.hide()
        }
    }
}