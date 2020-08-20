package com.ss_eduhub.common

import com.ss_eduhub.model.PopularTagCategoryItem
import com.ss_eduhub.model.SignInItem

class Constants {
    companion object {
        const val PROGRESS_DIALOG = "progress_dialog"
        const val SUCCESS_DIALOG = "success_dialog"
        const val DELETE_DIALOG = "delete_dialog"
        const val CHANGE_PASSWORD_DIALOG = "change_password_dialog"
        const val CHANGE_PHONE_NUMBER_DIALOG = "change_phone_number_dialog"
        const val LOGOUT_DIALOG = "logout_dialog"
        const val EMPTY_DOWNLOAD_DIALOG = "empty_download_dialog"
        const val CLASS_ITEM = "class_item"
        const val VIDEO_ITEM = "video_item"
        const val INSTITUTE_NAME = "institute_name"
        var userProfileData = SignInItem()
        var categoryId = PopularTagCategoryItem()
    }
}