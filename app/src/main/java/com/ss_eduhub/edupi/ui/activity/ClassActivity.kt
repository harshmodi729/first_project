package com.ss_eduhub.edupi.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseActivity
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.common.Constants
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.model.PopularClassesItem
import com.ss_eduhub.edupi.ui.fragment.LessonFragment
import com.ss_eduhub.edupi.ui.fragment.OverviewFragment
import com.ss_eduhub.edupi.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.activity_class.*


class ClassActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var item: PopularClassesItem
    private lateinit var videoViewModel: VideoViewModel

    companion object {
        private const val ADD_TO_WISH_LIST = 1
        private const val REMOVE_FROM_WISH_LIST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        videoViewModel = ViewModelProviders.of(this)[VideoViewModel::class.java]
        videoViewModel.addWishListLiveData.observe(this, Observer {
            hideProgressDialog(ivDialogBg)
            btnFavorite.setOnCheckedChangeListener(null)
            when (it) {
                is BaseResult.Success -> {
                    if (btnFavorite.isChecked)
                        makeToast("Added in wish list.")
                    else
                        makeToast("Remove from wish list.")
                }
                is BaseResult.Error -> {
                    btnFavorite.isChecked = !btnFavorite.isChecked
                    makeToast(it.errorMessage)
                }
            }
            btnFavorite.setOnCheckedChangeListener(this)
        })

        if (intent.extras != null) {
            item = intent.getSerializableExtra(Constants.CLASS_ITEM) as PopularClassesItem
            tvTutorName.text = item.author
            ratingClass.rating = item.ratings.toFloat()
            tvTotalVideos.text = item.videosCount.toString().plus(" Videos")
            tvClassDescription.text = item.shortIntro
            btnFavorite.isChecked = item.wishList == ADD_TO_WISH_LIST
        }
        btnFavorite.setOnCheckedChangeListener(this)
        val adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(OverviewFragment(item))
        adapter.addFragment(LessonFragment(item))
        classPager.adapter = adapter
        TabLayoutMediator(tabClass, classPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = getString(R.string.overview)
                1 -> tab.text = getString(R.string.lessons)
            }
        }.attach()
        tabClass.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    groupLesson.visibility = View.VISIBLE
                    btnStartLearning.visibility = View.GONE
                } else {
                    groupLesson.visibility = View.GONE
                    btnStartLearning.visibility = View.VISIBLE
                }
            }
        })

        Glide.with(this)
            .asBitmap()
            .load(item.thumbnail)
            .placeholder(R.drawable.ic_no_image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    ivClass.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        tvTutorName.setOnClickListener {
            startActivity(Intent(this, InstituteProfileActivity::class.java))
        }
        btnTakeClass.setOnClickListener {
            startActivity(
                Intent(this, PurchaseClassActivity::class.java)
                    .putExtra(Constants.CLASS_ITEM, item)
            )
        }
    }

    class ViewPagerFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val arrayList: ArrayList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment) {
            arrayList.add(fragment)
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        override fun createFragment(position: Int) = arrayList[position]
    }

    override fun onCheckedChanged(btn: CompoundButton, isChecked: Boolean) {
        val isAdd = if (isChecked) ADD_TO_WISH_LIST else REMOVE_FROM_WISH_LIST
        videoViewModel.addToWishList(item.classId, isAdd)
    }
}