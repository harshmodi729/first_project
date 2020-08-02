package com.hmatter.first_project.ui.fragment

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
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.*
import com.hmatter.first_project.model.SignInItem
import com.hmatter.first_project.ui.activity.StartupActivity
import com.hmatter.first_project.viewmodel.AccountSettingsViewModel
import com.hmatter.first_project.viewmodel.ForgotPasswordViewModel
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

        accountSettingsViewModel.getUserProfileData(mContext)
        accountSettingsViewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    tvUserName.text = it.item.name
                    panelPhoneNumber.edChangeProfile.setText(it.item.mobile)
                    panelEmail.edChangeProfile.setText(it.item.email)
                    panelPassword.edChangeProfile.setText(it.item.password)
                    if (it.item.profile.isBlankOrEmpty()) {
                        ivProfile.setImageResource(R.drawable.person)
                    } else {
                        Glide.with(mContext)
                            .load(it.item.profile)
                            .centerCrop()
                            .into(ivProfile)
                    }
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
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
                    mContext.makeToast(it.item)
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
                item = SignInItem()
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
                checkPermissions()
            else
                mContext.makeToast("You've denied the permission.")
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
                mContext.permissionDeniedDialog()
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
                accountSettingsViewModel.uploadImage(mContext, filePart).apply {
//                    showProgressDialog(layAccountSettings, ivDialogBg)
                }

                Glide.with(mContext)
                    .load(uri)
                    .centerCrop()
                    .into(ivProfile)
            }
        }
    }
}