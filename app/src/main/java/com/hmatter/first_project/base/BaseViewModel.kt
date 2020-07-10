package com.hmatter.first_project.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmatter.first_project.remote.ApiManager
import com.hmatter.first_project.remote.ApiServices
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun getApiServiceManager(): ApiServices {
        return ApiManager.getApiServices()
    }

    /**
     * This method will get bitmap from rootView and make it blur 25f
     * @param context Activity's Context
     * @param rootView of selected screen portion that will be blur
     * @param ivBackground set blur bitmap on [ImageView]
     */
    open fun setBlurImage(context: Context, rootView: View, ivBackground: AppCompatImageView) {
        var originalBitmap: Bitmap? = null
        viewModelScope.launch {
            originalBitmap =
                Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
            originalBitmap?.let {
                val canvas = Canvas(it)
                rootView.draw(canvas)

                val rs = RenderScript.create(context)
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
}