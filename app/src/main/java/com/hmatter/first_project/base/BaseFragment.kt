package com.hmatter.first_project.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.hmatter.first_project.R
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.extension.getProgressDialog
import com.hmatter.first_project.extension.setBlurImage

abstract class BaseFragment(layResourceId: Int) : Fragment(layResourceId) {

    lateinit var mContext: Activity
    private var progressDialog: AlertDialog? = null
    private var successDialog: AlertDialog? = null
    private var changePasswordDialog: AlertDialog? = null

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
        changePasswordDialog = mContext.getProgressDialog(R.layout.lay_dialog_changepassword,getString(R.string.change_password_text),Constants.CHANGEPASSWORD_DIALOG)
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
            progressDialog?.hide()
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
            successDialog?.hide()
        }
    }

    fun showDeleteDialog(
        rootView: View,
        ivDialogBg: AppCompatImageView
    ) {
        if (!successDialog?.isShowing!!) {
            mContext.setBlurImage(rootView, ivDialogBg)
            successDialog?.show()
        }
    }

    fun showChangePasswordDialog(rootView: View,ivDialogBg: AppCompatImageView){
        if(!changePasswordDialog?.isShowing!!){
            mContext.setBlurImage(rootView,ivDialogBg)
            changePasswordDialog?.show()
        }
    }

    fun hideChangePasswordDialog(ivDialogBg: AppCompatImageView){
        if(changePasswordDialog?.isShowing!!){
            ivDialogBg.visibility = View.GONE
            changePasswordDialog?.hide()
        }
    }


}