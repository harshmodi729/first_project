package com.hmatter.first_project.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.DownloadAdapter
import com.hmatter.first_project.adapter.FavoriteClassesAdapter
import com.hmatter.first_project.adapter.VideoCategoryAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.extension.isBlankOrEmpty
import com.hmatter.first_project.extension.loadImage
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.ui.activity.ClassActivity
import com.hmatter.first_project.viewmodel.HomeViewModel
import com.hmatter.first_project.viewmodel.SearchViewModel
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