package com.ss_eduhub.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.ss_eduhub.R
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.getProgressDialog
import com.ss_eduhub.extension.setBlurImage

abstract class BaseFragment(layResourceId: Int) : Fragment(layResourceId) {

    lateinit var mContext: Activity
    private var progressDialog: AlertDialog? = null
    private var successDialog: AlertDialog? = null
    private var changePasswordDialog: AlertDialog? = null
    private var logoutDialog: AlertDialog? = null
    private var cellularDataConfirmationDialog: AlertDialog? = null
    private var emptyDownloadDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = context as BaseActivity
        progressDialog = mContext.getProgressDialog(R.layout.lay_dialog_progress)
        successDialog = mContext.getProgressDialog(
            R.layout.lay_dialog_success,
            getString(R.string.account_created_successfully),
            Constants.SUCCESS_DIALOG
        )
        logoutDialog = mContext.getProgressDialog(
            R.layout.lay_dialog_logout,
            dialogType = Constants.LOGOUT_DIALOG
        )
        emptyDownloadDialog = mContext.getProgressDialog(
            R.layout.lay_dialog_empty_download,
            dialogType = Constants.EMPTY_DOWNLOAD_DIALOG
        )
        cellularDataConfirmationDialog = mContext.getProgressDialog(
            R.layout.lay_dialog_cellular_data_confirmation,
            dialogType = Constants.CELLULAR_DATA_CONFIRMATION_DIALOG
        )

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showProgressDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!progressDialog?.isShowing!!) {
            mContext.setBlurImage(rootView, ivDialogBg)
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
            mContext.setBlurImage(rootView, ivDialogBg)
            successDialog?.show()
        }
    }

    fun hideSuccessDialog(ivDialogBg: AppCompatImageView) {
        if (successDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            successDialog?.dismiss()
        }
    }

    fun showLogoutDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!logoutDialog?.isShowing!!) {
            mContext.setBlurImage(rootView, ivDialogBg)
            logoutDialog?.show()
        }
    }

    fun hideLogoutDialog(ivDialogBg: AppCompatImageView) {
        if (logoutDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            logoutDialog?.dismiss()
        }
    }

    fun showChangePasswordDialog(rootView: View, ivDialogBg: AppCompatImageView) {
        changePasswordDialog = mContext.getProgressDialog(
            R.layout.lay_dialog_change_password,
            getString(R.string.change_password_text),
            Constants.CHANGE_PASSWORD_DIALOG
        )
        mContext.setBlurImage(rootView, ivDialogBg)
        changePasswordDialog?.show()
    }

    fun hideChangePasswordDialog(ivDialogBg: AppCompatImageView) {
        if (changePasswordDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            changePasswordDialog?.dismiss()
        }
    }

    fun showEmptyDownloadDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!emptyDownloadDialog?.isShowing!!) {
            mContext.setBlurImage(rootView, ivDialogBg)
            emptyDownloadDialog?.show()
        }
    }

    fun hideEmptyDownloadDialog(ivDialogBg: AppCompatImageView) {
        if (emptyDownloadDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            emptyDownloadDialog?.dismiss()
        }
    }

    fun showCellularDataConfirmationDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!cellularDataConfirmationDialog?.isShowing!!) {
            mContext.setBlurImage(rootView, ivDialogBg)
            cellularDataConfirmationDialog?.show()
        }
    }

    fun hideCellularDataConfirmationDialog(ivDialogBg: AppCompatImageView) {
        if (cellularDataConfirmationDialog?.isShowing!!) {
            ivDialogBg.visibility = View.GONE
            cellularDataConfirmationDialog?.dismiss()
        }
    }
}