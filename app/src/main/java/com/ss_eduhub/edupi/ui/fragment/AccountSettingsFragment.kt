package com.ss_eduhub.edupi.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseFragment
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.extension.*
import com.ss_eduhub.edupi.model.SignInItem
import com.ss_eduhub.edupi.ui.activity.StartupActivity
import com.ss_eduhub.edupi.viewmodel.AccountSettingsViewModel
import com.ss_eduhub.edupi.viewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.lay_account_setting_edit_detail.view.*
import kotlinx.android.synthetic.main.lay_dialog_verify_phone_number.view.*
import kotlinx.android.synthetic.main.lay_toolbar.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class AccountSettingsFragment : BaseFragment(R.layout.fragment_account_settings) {

    private lateinit var item: SignInItem
    private lateinit var accountSettingsViewModel: AccountSettingsViewModel
    private lateinit var changePhoneNumberDialog: AlertDialog
    private lateinit var dialogView: View

    companion object {
        private const val SELECT_PHOTO = 1001
        private const val PERMISSION_REQUEST = 1002
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.account_settings)

        accountSettingsViewModel =
            ViewModelProviders.of(this)[AccountSettingsViewModel::class.java]
        val changePasswordViewModel =
            ViewModelProviders.of(this)[ForgotPasswordViewModel::class.java]

        item = Constants.userProfileData
        tvUserName.text = item.name
        panelPhoneNumber.edChangeProfile.setText(item.mobile)
        panelEmail.edChangeProfile.setText(item.email)
        panelPassword.edChangeProfile.setText(item.password)
        if (!item.profile.isBlankOrEmpty()) {
            Glide.with(mContext)
                .load(item.profile)
                .centerCrop()
                .into(ivProfile)
        }
        accountSettingsViewModel.signOutLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    startActivity(
                        Intent(mContext, StartupActivity::class.java).putExtra(
                            "isAlreadyVisitIntro", true
                        )
                    )
                    mContext.finish()
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        accountSettingsViewModel.uploadImageLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    item.profile = it.item
                    mContext.makeToast("Image upload successfully.")
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        accountSettingsViewModel.changePhoneNumberLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    dialogView.groupVerifyNumber.visibility = View.GONE
                    dialogView.layOtp.visibility = View.VISIBLE
                    mContext.hideKeyboard(dialogView.tvOtpDescription)
                    val description =
                        String.format(getString(R.string.otp_heading_text), item.mobile)
                    dialogView.tvOtpDescription.text = description.bold(item.mobile)
                    dialogView.otp.setOtpCompletionListener {
                        changePhoneNumberDialog.dismiss()
                    }
                    dialogView.otpResend.setOnClickListener {
                        mContext.makeToast("Otp resend request successfully.")
                    }
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        changePasswordViewModel.resetPasswordLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    hideChangePasswordDialog(ivDialogBg)
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })

        btnEditImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                openImageChooser()
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, SELECT_PHOTO)
            }
        }
        btnToolbarBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        btnLogout.setOnClickListener {
            showLogoutDialog(layAccountSettings, ivDialogBg)
        }
        panelPhoneNumber.btnEdit.setOnClickListener {
            mContext.setBlurImage(layAccountSettings, ivDialogBg)
            changePhoneNumberDialog =
                AlertDialog.Builder(mContext, R.style.DialogStyle).create()
            dialogView = View.inflate(mContext, R.layout.lay_dialog_verify_phone_number, null)
            changePhoneNumberDialog.setOnCancelListener {
                ivDialogBg.visibility = View.GONE
            }
            dialogView.edPassword.setText("")
            dialogView.edPhone.setText("")
            changePhoneNumberDialog.setView(dialogView)
            dialogView.btnVerify.setOnClickListener {
                item.password = dialogView.edPassword.text.toString()
                item.mobile = dialogView.edPhone.text.toString()
                showProgressDialog(layAccountSettings, ivDialogBg)
                accountSettingsViewModel.changePhoneNumber(mContext, item)
            }
            changePhoneNumberDialog.show()
        }
        panelPassword.btnEdit.setOnClickListener {
            showChangePasswordDialog(layAccountSettings, ivDialogBg)
        }

        onDialogButtonClick = {
            hideLogoutDialog(ivDialogBg)
            if (it) {
                accountSettingsViewModel.userSignOut(mContext)
            }
        }

        onChangePasswordDialogButtonClick = { isPositive, forgotPasswordItem ->
            if (isPositive)
                changePasswordViewModel.resetPassword(mContext, forgotPasswordItem, false)
            else
                hideChangePasswordDialog(ivDialogBg)
        }

        onDeniedDialogClick = { isPositive ->
            if (isPositive)
                openImageChooser()
            else
                mContext.makeToast("You've denied the permission.")
        }

        onPermanentlyDeniedDialogClick = {
            mContext.openAppSettings()
        }
    }

    private fun openImageChooser() {
        if (checkPermissions()) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_PHOTO)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission()
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val writeStorage = ContextCompat.checkSelfPermission(
            mContext,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return writeStorage == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST && grantResults.isNotEmpty()) {
            val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (storageAccepted) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, SELECT_PHOTO)
                return
            }
            if (!storageAccepted) {
                if (!shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mContext.permissionDeniedDialog(
                        "Permission Denied",
                        "From application settings in permissions block set Storage permission ON. Press \"Settings\" and allow the permission.",
                        "Settings",
                        "Cancel"
                    )
                } else {
                    mContext.permissionDeniedDialog(
                        "Permission Denied",
                        "To change the profile image please allow the permission. Press \"Retry\" and allow the permission.",
                        "Retry",
                        "Cancel"
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                val uri = data.data
                val cursor =
                    mContext.contentResolver.query(
                        uri!!,
                        arrayOf(MediaStore.Images.Media.DATA),
                        null,
                        null,
                        null
                    )
                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                val s: String = cursor.getString(columnIndex)
                cursor.close()

                val file = File(s)
                val fileBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData(
                    "profile",
                    file.name, fileBody
                )
                accountSettingsViewModel.uploadImage(mContext, filePart)

                Glide.with(mContext)
                    .load(uri)
                    .centerCrop()
                    .into(ivProfile)
            }
        }
    }
}