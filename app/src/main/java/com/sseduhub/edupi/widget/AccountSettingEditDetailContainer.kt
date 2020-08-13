package com.sseduhub.edupi.widget

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sseduhub.edupi.R

class AccountSettingEditDetailContainer(
    context: Context,
    attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    companion object {
        private const val PHONE_NUMBER = 0
        private const val EMAIL = 1
        private const val PASSWORD = 2
    }

    private var tvLabel: AppCompatTextView
    private var edChangeProfile: AppCompatEditText
    private var btnEdit: AppCompatImageButton

    init {
        inflate(context, R.layout.lay_account_setting_edit_detail, this)

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.AccountSettingEditDetailContainer)
        tvLabel = findViewById(R.id.tvLabel)
        edChangeProfile = findViewById(R.id.edChangeProfile)
        btnEdit = findViewById(R.id.btnEdit)

        tvLabel.text = attributes.getString(R.styleable.AccountSettingEditDetailContainer_label)
        edChangeProfile.setText(attributes.getString(R.styleable.AccountSettingEditDetailContainer_value))
        edChangeProfile.isEnabled =
            attributes.getBoolean(R.styleable.AccountSettingEditDetailContainer_enabled, false)
        btnEdit.visibility =
            if (attributes.getBoolean(
                    R.styleable.AccountSettingEditDetailContainer_isEditable,
                    true
                )
            ) View.VISIBLE else View.GONE
        edChangeProfile.hint =
            attributes.getString(R.styleable.AccountSettingEditDetailContainer_hint)
        val type =
            attributes.getInt(R.styleable.AccountSettingEditDetailContainer_type, PHONE_NUMBER)
        when (type) {
            PHONE_NUMBER -> edChangeProfile.inputType = InputType.TYPE_CLASS_NUMBER
            EMAIL -> edChangeProfile.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            PASSWORD -> edChangeProfile.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        attributes.recycle()
    }
}