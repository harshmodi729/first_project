package com.hmatter.first_project.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.hmatter.first_project.R
import com.hmatter.first_project.common.Constants
import kotlinx.android.synthetic.main.lay_dialog_change_password.view.*
import kotlinx.android.synthetic.main.lay_dialog_delete.view.*
import kotlinx.android.synthetic.main.lay_dialog_delete.view.btnCancel
import kotlinx.android.synthetic.main.lay_dialog_logout.view.*
import kotlinx.android.synthetic.main.lay_dialog_success.view.*
import kotlinx.coroutines.runBlocking

/**
 * Invoke toast message on screen whatever text provided when calling it
 * @param text that'll print within toast
 * @param length by default short, can be set up to user
 */
fun Context.makeToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
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
var onChangePasswordDialogButtonClick: ((
    isPositive: Boolean,
    dialogView: View
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
                onChangePasswordDialogButtonClick?.invoke(true, view)
            }
            view.btnCancel.setOnClickListener {
                onChangePasswordDialogButtonClick?.invoke(false, view)
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