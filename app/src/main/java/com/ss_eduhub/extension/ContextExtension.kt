package com.ss_eduhub.extension

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ParseException
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.text.Html
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.Constants
import com.ss_eduhub.model.CommentItem
import com.ss_eduhub.model.ForgotPasswordItem
import kotlinx.android.synthetic.main.lay_dialog_change_password.view.*
import kotlinx.android.synthetic.main.lay_dialog_comment_rating.view.*
import kotlinx.android.synthetic.main.lay_dialog_delete.view.*
import kotlinx.android.synthetic.main.lay_dialog_delete.view.btnCancel
import kotlinx.android.synthetic.main.lay_dialog_empty_download.view.*
import kotlinx.android.synthetic.main.lay_dialog_logout.view.*
import kotlinx.android.synthetic.main.lay_dialog_success.view.*
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.UnknownHostException


/**
 * Invoke toast message on screen whatever text provided when calling it
 * @param text that'll print within toast
 * @param length by default short, can be set up to user
 */
fun Context.makeToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.makeToastForServerError(error: BaseResult.Error) {
    var errorMessage = "Oops something went wrong."
    if (error.exception is IOException) {
        when (error.exception) {
            is UnknownHostException, is InterruptedIOException -> {
                this.permissionDeniedDialog(
                    "No Internet",
                    "Check your internet connection and try again.",
                    "Settings",
                    "Cancel"
                )
                onPermanentlyDeniedDialogClick = {
                    this.openSettings()
                }
                errorMessage = "No internet connection."
            }
        }
    } else if (error.exception is HttpException) {
        errorMessage = "Oops something went wrong."
    } else if (error.exception is JSONException ||
        error.exception is JsonParseException ||
        error.exception is JsonSyntaxException ||
        error.exception is ParseException
    ) {
        errorMessage = "Something wrong with response."
    } else {
        if (error.errorMessage != null && !error.errorMessage.isBlankOrEmpty())
            errorMessage = error.errorMessage
    }
    this.makeToast(errorMessage)
}

/**
 * This method will get bitmap from rootView and make it blur 25f
 * @param rootView of selected screen portion that will be blur
 * @param ivBackground set blur bitmap on [ImageView]
 */
fun Context.setBlurImage(rootView: View, ivBackground: AppCompatImageView) {
    var originalBitmap: Bitmap? = null
    val mContext = this
    runBlocking {
        originalBitmap =
            Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
        originalBitmap?.let {
            val canvas = Canvas(it)
            rootView.draw(canvas)

            val rs = RenderScript.create(mContext)
            val input = Allocation.createFromBitmap(rs, originalBitmap)
            val output = Allocation.createTyped(rs, input.type)
            val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            script.setRadius(25f)
            script.setInput(input)
            script.forEach(output)
            output.copyTo(originalBitmap)
        }
    }
    ivBackground.visibility = View.VISIBLE
    ivBackground.setImageBitmap(originalBitmap)
}

/**
 * Invoke progress dialog
 */
var onDialogButtonClick: ((isPositive: Boolean) -> Unit)? = null
var onDialogWithDataButtonClick: ((isPositive: Boolean, any: Any?) -> Unit)? = null
var onChangePasswordDialogButtonClick: ((
    isPositive: Boolean,
    item: ForgotPasswordItem
) -> Unit)? = null

fun Context.getProgressDialog(
    layout: Int,
    message: String = "",
    dialogType: String = Constants.PROGRESS_DIALOG
): AlertDialog {
    val dialog = AlertDialog.Builder(this, R.style.DialogStyle).create()
    val view = View.inflate(this, layout, null)
    when (dialogType) {
        Constants.SUCCESS_DIALOG -> {
            view.tvSuccessDialogMessage.text = message
            view.btnSuccessDialog.setOnClickListener {
                onDialogButtonClick?.invoke(true)
            }
        }
        Constants.DELETE_DIALOG -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                view.tvDeleteLabel.text = Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY)
            else
                view.tvDeleteLabel.text = Html.fromHtml(message)
            view.btnDelete.setOnClickListener {
                onDialogButtonClick?.invoke(true)
            }
            view.btnCancel.setOnClickListener {
                onDialogButtonClick?.invoke(false)
            }
        }
        Constants.CHANGE_PASSWORD_DIALOG -> {
            view.btnOkay.setOnClickListener {
                val forgotPasswordItem = ForgotPasswordItem()
                forgotPasswordItem.oldPassword = view.edOldPassword.text.toString().trim()
                forgotPasswordItem.newPassword = view.edNewPassword.text.toString().trim()
                forgotPasswordItem.confirmPassword = view.edCnfPassword.text.toString().trim()
                onChangePasswordDialogButtonClick?.invoke(true, forgotPasswordItem)
            }
            view.btnCancel.setOnClickListener {
                onChangePasswordDialogButtonClick?.invoke(false, ForgotPasswordItem())
            }
        }
        Constants.LOGOUT_DIALOG -> {
            view.btnLogout.setOnClickListener {
                onDialogButtonClick?.invoke(true)
            }
            view.btnCancel.setOnClickListener {
                onDialogButtonClick?.invoke(false)
            }
        }
        Constants.EMPTY_DOWNLOAD_DIALOG -> {
            view.btnEmptyDownload.setOnClickListener {
                onDialogButtonClick?.invoke(true)
            }
        }
        Constants.COMMENT_RATING_DIALOG -> {
            view.tvCommentRatingLabel.text = message
            view.ratingClass.rating = 0F
            view.edComment.setText("")
            val commentItem = CommentItem()
            var rating = 0.0
            view.ratingClass.setOnRatingChangeListener { _, value ->
                rating = value.toDouble()
            }
            view.btnSubmit.setOnClickListener {
                commentItem.userComment = view.edComment.text.toString().trim()
                commentItem.userRating = rating
                onDialogWithDataButtonClick?.invoke(true, commentItem)
            }
            view.btnCancel.setOnClickListener {
                onDialogWithDataButtonClick?.invoke(false, null)
            }
        }
    }
    dialog.setView(view)
    dialog.setCancelable(false)
    return dialog
}

/**
 * Method is invoke when app needs to hide a keyboard
 * @param view
 */
fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * If user denied permission or select don't ask again box and denied, at that time,
 * invoke this permission denial information dialog
 */
var onDeniedDialogClick: ((isPositive: Boolean) -> Unit)? = null
var onPermanentlyDeniedDialogClick: (() -> Unit)? = null
private var permissionDialog: AlertDialog? = null
fun Context.permissionDeniedDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String
) {
    if (permissionDialog != null && permissionDialog!!.isShowing) {
        permissionDialog!!.dismiss()
        permissionDialog = null
    }
    permissionDialog = AlertDialog.Builder(this).create()
    permissionDialog!!.setCancelable(false)
    permissionDialog!!.setTitle(title)
    permissionDialog!!.setMessage(message)
    permissionDialog!!.setButton(AlertDialog.BUTTON_POSITIVE, positiveButtonText) { _, _ ->
        if (positiveButtonText == "Settings")
            onPermanentlyDeniedDialogClick?.invoke()
        else
            onDeniedDialogClick?.invoke(true)
        permissionDialog!!.dismiss()
    }
    permissionDialog!!.setButton(AlertDialog.BUTTON_NEGATIVE, negativeButtonText) { _, _ ->
        onDeniedDialogClick?.invoke(false)
        permissionDialog!!.dismiss()
    }
    permissionDialog!!.show()
    permissionDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
    permissionDialog!!.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
}

/**
 * This method will open device settings screen of [edupi] application
 */
fun Context.openAppSettings() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    this.startActivity(intent)
}

/**
 * This method will open device main setting screen
 */
fun Context.openSettings() {
    startActivity(Intent(Settings.ACTION_SETTINGS))
}

/**
 * This method will retrieve image url and set on given [ImageView]
 * @param imagePath url [String] of an image
 * @param imageView
 * @param diskCacheStrategy By Default, Glide caches images with URLs as keys in the memory and disk.
 *                          Tell Glide not to write any images to the cache with ->
 *                          [DiskCacheStrategy.NONE]
 */
fun Context.loadImage(
    imagePath: String?,
    imageView: AppCompatImageView,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC
) {
    try {
        Glide.with(this)
            .load(imagePath)
            .centerCrop()
            .fallback(R.drawable.ic_no_image)
            .placeholder(R.drawable.ic_no_image)
            .diskCacheStrategy(diskCacheStrategy)
            .into(imageView)
    } catch (exception: Exception) {
        exception.printStackTrace()
        imageView.setImageResource(R.drawable.ic_no_image)
    }
}

/**
 * This method will convert given [Float] value (in px) into DP
 */
fun Context.convertPixelsToDp(px: Float): Float {
    return px / (this.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method will convert given [Float] value (in DP) into px
 */
fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (this.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}