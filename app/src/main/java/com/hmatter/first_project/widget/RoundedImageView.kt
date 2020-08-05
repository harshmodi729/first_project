package com.hmatter.first_project.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class RoundedImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(getRoundCornerBitmap(bm))
    }

    private fun getRoundCornerBitmap(bitmap: Bitmap): Bitmap? {
        val w = bitmap.width
        val h = bitmap.height
        val output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rectF = RectF(0F, 0F, w.toFloat(), h.toFloat())
        canvas.drawRoundRect(rectF, 50F, 50F, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, null, rectF, paint)
        /**
         * define corners here, for left and right bottom corners
         */
        val clipRect = Rect(0, 0, w, h - 50F.toInt())
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        canvas.drawRect(clipRect, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, null, rectF, paint)
        return output
    }
}