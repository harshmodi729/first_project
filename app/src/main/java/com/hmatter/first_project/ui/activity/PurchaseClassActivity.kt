package com.hmatter.first_project.ui.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.model.PopularClassesItem
import kotlinx.android.synthetic.main.activity_purchase_class.*

class PurchaseClassActivity : BaseActivity() {
    private lateinit var item: PopularClassesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_class)

        if (intent.extras != null) {
            item = intent.getSerializableExtra(Constants.CLASS_ITEM) as PopularClassesItem
            Glide.with(this)
                .asBitmap()
                .load(item.thumbnail)
                .placeholder(R.drawable.ic_no_image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        ivClass.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }
}