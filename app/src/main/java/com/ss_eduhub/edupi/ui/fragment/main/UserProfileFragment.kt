package com.ss_eduhub.edupi.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.adapter.DownloadAdapter
import com.ss_eduhub.edupi.adapter.FavoriteClassesAdapter
import com.ss_eduhub.edupi.adapter.VideoCategoryAdapter
import com.ss_eduhub.edupi.base.BaseFragment
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.extension.isBlankOrEmpty
import com.ss_eduhub.edupi.extension.loadImage
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.ui.activity.ClassActivity
import com.ss_eduhub.edupi.viewmodel.HomeViewModel
import com.ss_eduhub.edupi.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    private lateinit var favoriteCLassesAdapter: FavoriteClassesAdapter
    private lateinit var videoCategoryAdapter: VideoCategoryAdapter
    private lateinit var yourClassesAdapter: DownloadAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteCLassesAdapter = FavoriteClassesAdapter(mContext)
        rvFavorites.adapter = favoriteCLassesAdapter
        videoCategoryAdapter = VideoCategoryAdapter(mContext)
        rvVideoCategory.adapter = videoCategoryAdapter
        yourClassesAdapter = DownloadAdapter(mContext, false)
        rvYourClasses.adapter = yourClassesAdapter

        if (!Constants.userProfileData.profile.isBlankOrEmpty()) {
            mContext.loadImage(Constants.userProfileData.profile, ivBigUserProfile)
        }
        tvToolbarTitle.text = Constants.userProfileData.name

        val homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
        val searchViewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]

        homeViewModel.getFavoriteClasses().apply {
            layUserProfile.post {
                showProgressDialog(layUserProfile, ivDialogBg)
            }
        }
        homeViewModel.favoriteClassesLiveData.observe(viewLifecycleOwner, Observer {
            tvFavoritesLabel.visibility = View.VISIBLE
            rvFavorites.visibility = View.VISIBLE
            when (it) {
                is BaseResult.Success -> {
                    if (it.item.isEmpty()) {
                        tvFavoritesLabel.visibility = View.GONE
                        rvFavorites.visibility = View.GONE
                    } else
                        favoriteCLassesAdapter.addData(it.item)
                }
                is BaseResult.Error -> {
                    tvFavoritesLabel.visibility = View.GONE
                    rvFavorites.visibility = View.GONE
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        yourClassesAdapter.addData((1..5).map { "Item $it" } as ArrayList<String>)

        searchViewModel.getVideoCategories()
        searchViewModel.videoCategoryLiveData.observe(
            viewLifecycleOwner,
            Observer {
                hideProgressDialog(ivDialogBg)
                when (it) {
                    is BaseResult.Success -> {
                        videoCategoryAdapter.addData(it.item)
                    }
                    is BaseResult.Error -> {
                        mContext.makeToast(it.errorMessage)
                    }
                }
            })

        favoriteCLassesAdapter.onFavoriteClassClick = { item ->
            item.wishList = 1
            mContext.startActivity(
                Intent(mContext, ClassActivity::class.java).putExtra(Constants.CLASS_ITEM, item)
            )
        }
    }
}