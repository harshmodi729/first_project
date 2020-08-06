package com.hmatter.first_project.ui.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import com.hmatter.first_project.common.Constants
import com.hmatter.first_project.model.PopularClassesItem
import com.hmatter.first_project.ui.fragment.LessonFragment
import com.hmatter.first_project.ui.fragment.OverviewFragment
import kotlinx.android.synthetic.main.activity_class.*


class ClassActivity : BaseActivity() {
    private lateinit var item: PopularClassesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)

        if (intent.extras != null) {
            item = intent.getSerializableExtra(Constants.CLASS_ITEM) as PopularClassesItem
            tvTutorName.text = item.author
            ratingClass.rating = item.ratings.toFloat()
            tvTotalVideos.text = item.videosCount.toString().plus(" Videos")
            tvClassDescription.text = item.shortIntro
        }
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
            .load("https://d1zdxptf8tk3f9.cloudfront.net/ckeditor_assets/pictures/2509/content_geordanna-cordero-fields-762612-unsplash.jpg")
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView1.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }

    class ClassPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val alFragment = ArrayList<Fragment>()
        private val alFragmentTitle = ArrayList<String>()

        override fun getItem(position: Int) = alFragment[position]

        override fun getCount() = alFragment.size

        override fun getPageTitle(position: Int) = alFragmentTitle[position]

        fun addFragment(fragment: Fragment, title: String) {
            alFragment.add(fragment)
            alFragmentTitle.add(title)
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
}