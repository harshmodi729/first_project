package com.ss_eduhub.common

import com.ss_eduhub.model.PopularTagCategoryItem
import com.ss_eduhub.model.SignInItem
import com.ss_eduhub.model.VideosItem

class Constants {
    companion object {
        const val PROGRESS_DIALOG = "progress_dialog"
        const val SUCCESS_DIALOG = "success_dialog"
        const val DELETE_DIALOG = "delete_dialog"
        const val CHANGE_PASSWORD_DIALOG = "change_password_dialog"
        const val CHANGE_PHONE_NUMBER_DIALOG = "change_phone_number_dialog"
        const val LOGOUT_DIALOG = "logout_dialog"
        const val EMPTY_DOWNLOAD_DIALOG = "empty_download_dialog"
        const val COMMENT_RATING_DIALOG = "comment_rating_dialog"
        const val CELLULAR_DATA_CONFIRMATION_DIALOG = "cellular_data_confirmation_dialog"
        const val CLASS_ITEM = "class_item"
        const val VIDEO_ITEM = "video_item"
        const val INSTITUTE_NAME = "institute_name"
        const val COURSE_ITEM = "course_item"
        const val COURSE_TEST_ITEM = "course_test_item"
        const val QUESTION_ITEM = "question_item"
        const val QUESTION_LIST = "question_list"
        var isPurchased = false
        val downloadingItem = HashMap<Long, VideosItem>()
        var userProfileData = SignInItem()
        var categoryId = PopularTagCategoryItem()
    }
}