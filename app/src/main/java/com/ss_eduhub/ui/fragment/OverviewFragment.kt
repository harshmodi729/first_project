package com.ss_eduhub.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProviders
import com.ss_eduhub.R
import com.ss_eduhub.adapter.CommentAdapter
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.loadImage
import com.ss_eduhub.extension.makeToast
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.extension.onDialogWithDataButtonClick
import com.ss_eduhub.model.CommentItem
import com.ss_eduhub.model.PopularClassesItem
import com.ss_eduhub.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.activity_class.*
import kotlinx.android.synthetic.main.activity_class.ratingClass
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_overview.ivDialogBg
import kotlinx.android.synthetic.main.fragment_overview.tvTotalVideos
import kotlinx.android.synthetic.main.lay_dialog_comment_rating.*
import kotlinx.android.synthetic.main.lay_dialog_comment_rating.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OverviewFragment(private val classesItem: PopularClassesItem) :
    BaseFragment(R.layout.fragment_overview) {

    private lateinit var adapter: CommentAdapter
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var commentRatingDialog: AlertDialog
    private var rating = 0F
    private var comment = ""
    private val alComments = ArrayList<CommentItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        videoViewModel = ViewModelProviders.of(this)[VideoViewModel::class.java]
        videoViewModel.addCommentRatingLiveData.observe(viewLifecycleOwner, {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    if (it.item.equals("added", true)) {
                        val commentItem = CommentItem()
                        commentItem.rating = rating.toDouble()
                        commentItem.comments = comment
                        commentItem.profile = Constants.userProfileData.profile
                        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
                        commentItem.created = sdf.format(Calendar.getInstance().time)
                        alComments.add(0, commentItem)
                    } else {
                        alComments.map { item ->
                            if (item.userId == Constants.userProfileData.id) {
                                item.rating = commentRatingDialog.ratingClass.rating.toDouble()
                                item.comments = commentRatingDialog.edComment.text.toString().trim()
                                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
                                item.created = sdf.format(Calendar.getInstance().time)
                            }
                        }
                    }
                    adapter.addData(alComments)
                    commentRatingDialog.dismiss()
                }
                is BaseResult.Error -> {
                    mContext.makeToastForServerError(it)
                }
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvClassOverview.text =
                HtmlCompat.fromHtml(classesItem.description, 0)
        } else {
            tvClassOverview.text = Html.fromHtml(classesItem.description)
        }
        tvTotalVideos.text = String.format("%02d", classesItem.videosCount).plus(" video lessons")

        mContext.loadImage(Constants.userProfileData.profile, ivUserProfile)
        adapter = CommentAdapter(mContext)
        rvComments.adapter = adapter
        alComments.addAll(classesItem.comments)
        adapter.addData(alComments)

        btnWriteReview.setOnClickListener {
            rating = 0F
            comment = ""
            showCommentRatingDialog()
        }

        adapter.onEditClick = {
            rating = it.rating.toFloat()
            comment = it.comments
            showCommentRatingDialog()
        }
        onDialogWithDataButtonClick = { isPositive: Boolean, any: Any? ->
            if (isPositive) {
                val commentItem = any as CommentItem
                showProgressDialog(layOverview, ivDialogBg)
                videoViewModel.addCommentRating(
                    classesItem.classId,
                    commentItem.userComment,
                    commentItem.userRating
                )
            } else {
                commentRatingDialog.dismiss()
            }
        }
    }

    private fun showCommentRatingDialog() {
        commentRatingDialog = AlertDialog.Builder(mContext, R.style.DialogStyle).create()
        val view = View.inflate(mContext, R.layout.lay_dialog_comment_rating, null)

        view.tvCommentRatingLabel.text = "Give review to ${classesItem.author}"
        view.ratingClass.rating = rating
        view.edComment.setText(comment)
        view.btnSubmit.setOnClickListener {
            showProgressDialog(layOverview, ivDialogBg)
            videoViewModel.addCommentRating(
                classesItem.classId,
                view.edComment.text.toString().trim(),
                view.ratingClass.rating.toDouble()
            )
        }
        view.btnCancel.setOnClickListener {
            commentRatingDialog.dismiss()
        }
        commentRatingDialog.setView(view)
        commentRatingDialog.setCancelable(false)
        commentRatingDialog.show()
//        commentRatingDialog = mContext.getProgressDialog(
//            R.layout.lay_dialog_comment_rating,
//            "Give review to ${classesItem.author}",
//            dialogType = Constants.COMMENT_RATING_DIALOG
//        )
//        commentRatingDialog!!.show()
    }
}