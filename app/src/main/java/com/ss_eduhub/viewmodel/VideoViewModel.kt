package com.ss_eduhub.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.data.local.SSEduhubAppDatabase
import com.ss_eduhub.data.local.model.LocalVideosItem
import com.ss_eduhub.extension.isBlankOrEmpty
import kotlinx.coroutines.launch

class VideoViewModel : BaseViewModel() {

    val addWishListLiveData = MutableLiveData<BaseResult<String>>()
    val addCommentRatingLiveData = MutableLiveData<BaseResult<String>>()
    val videoWatchTimeLiveData = MutableLiveData<BaseResult<List<LocalVideosItem>>>()

    fun addToWishList(classId: Int, isAdd: Int) {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().addToWishList(
                    Constants.userProfileData.id,
                    classId,
                    isAdd
                )
                if (response.success) {
                    addWishListLiveData.value = BaseResult.Success(response.message)
                } else {
                    addWishListLiveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                addWishListLiveData.value = BaseResult.Error(exception)
            }
        }
    }

    fun addCommentRating(classId: Int, comment: String, rating: Double) {
        viewModelScope.launch {
            try {
                val validation = validationControl(comment, rating)
                if (!validation.first) {
                    addCommentRatingLiveData.value =
                        BaseResult.Error(IllegalStateException(), validation.second)
                } else {
                    val response = getApiServiceManager().addCommentRating(
                        Constants.userProfileData.id,
                        classId,
                        rating,
                        comment
                    )
                    if (response.get("response").asBoolean) {
                        addCommentRatingLiveData.value =
                            BaseResult.Success(response.get("data").asString)
                    } else {
                        addCommentRatingLiveData.value =
                            BaseResult.Error(
                                IllegalStateException(),
                                response.get("message").asString
                            )
                    }
                }
            } catch (exception: Exception) {
                addCommentRatingLiveData.value = BaseResult.Error(IllegalStateException(exception))
            }
        }
    }

    private fun validationControl(comment: String, rating: Double): Pair<Boolean, String> {
        var isValid = true
        var message = ""
        if (comment.isBlankOrEmpty()) {
            isValid = false
            message = "Please enter comment."
            return Pair(isValid, message)
        }
        if (rating <= 0.0) {
            isValid = false
            message = "Please select rating."
            return Pair(isValid, message)
        }
        return Pair(isValid, message)
    }

    fun saveWatchTime(context: Context, localVideosItem: LocalVideosItem) {
        viewModelScope.launch {
            try {
                val ssEduhubAppDatabase = SSEduhubAppDatabase.getInstance(context)
                ssEduhubAppDatabase?.getVideoDao!!.insert(localVideosItem)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun getVideoWatchTime(context: Context, lessonId: Int) {
        viewModelScope.launch {
            try {
                val ssEduhubAppDatabase = SSEduhubAppDatabase.getInstance(context)
                videoWatchTimeLiveData.value = BaseResult.Success(
                    ssEduhubAppDatabase?.getVideoDao!!.getLessonsVideoData(lessonId)
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                videoWatchTimeLiveData.value = BaseResult.Error(exception)
            }
        }
    }
}