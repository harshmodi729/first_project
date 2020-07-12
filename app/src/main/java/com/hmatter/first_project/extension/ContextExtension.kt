package com.hmatter.first_project.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.hmatter.first_project.R
import kotlinx.android.synthetic.main.lay_success_dialog.view.*
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
fun Context.getProgressDialog(
    layout: Int,
    message: String = "",
    isButtonAvailable: Boolean = false
): AlertDialog {
    val dialog = AlertDialog.Builder(this, R.style.DialogStyle).create()
    val view = View.inflate(this, layout, null)
    if (message.isNotEmpty()) {
        view.tvSuccessDialogMessage.text = message
        if (isButtonAvailable)
            view.btnSuccessDialog.setOnClickListener {
                onSuccessClick?.invoke()
            }
    }
    dialog.setView(view)
    dialog.setCancelable(false)
    return dialog
}

var onSuccessClick: (() -> Unit)? = null
fun Context.getSuccessDialog(message: String): AlertDialog {
    val dialog = AlertDialog.Builder(this, R.style.DialogStyle).create()
    val view = View.inflate(this, R.layout.lay_success_dialog, null)
    view.tvSuccessDialogMessage.text = message
    view.btnSuccessDialog.setOnClickListener {
        onSuccessClick?.invoke()
    }
    dialog.setView(view)
    dialog.setCancelable(false)
    return dialog
}