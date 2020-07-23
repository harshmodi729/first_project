package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.PopularTagsAdapter
import com.hmatter.first_project.adapter.VideoCategoryAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val tagList: ArrayList<String> = ArrayList()
    private val alSelectedCategory = ArrayList<Int>()
    private lateinit var videoCategoryAdapter: VideoCategoryAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.search)
        tvToolbarTitle.visibility = View.GONE
        edSearch.visibility = View.VISIBLE

        videoCategoryAdapter = VideoCategoryAdapter(mContext)
        rvVideoCategory.adapter = videoCategoryAdapter
        val searchViewModel = ViewModelProviders.of(this)[SearchViewModel::class.java]
        searchViewModel.getVideoCategories().apply {
            laySearch.post {
                showProgressDialog(laySearch, ivDialogBg)
            }
        }
        searchViewModel.alVideoCategoryItem.observe(
            viewLifecycleOwner,
            Observer {
                hideProgressDialog(ivDialogBg)
                when (it) {
                    is BaseResult.Success -> {
                        videoCategoryAdapter.addData(it.item)
                        rvVideoCategory.visibility = View.VISIBLE
                        tvTagView.visibility = View.GONE
                        rvTag.visibility = View.GONE
                    }
                    is BaseResult.Error -> {
                        mContext.makeToast(it.errorMessage)
                    }
                }
            })

        val cooking = resources.getStringArray(R.array.cooking_array)
        // Create the adapter and set it to the AutoCompleteTextView
        val adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, cooking) }
        edSearch.setAdapter(adapter)
        edSearch.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.layout_bg_dropdown
            )
        )

        tagList.add("Business & Management")
        tagList.add("Creative Art & Media")
        tagList.add("Health & Psychology")
        tagList.add("History")
        tagList.add("Language and Culture")
        tagList.add("Science Engineering & Maths")
        tagList.add("Study Skills")
        tagList.add("Tech & Coding")

        val layoutManager = LinearLayoutManager(mContext)
        rvTag.layoutManager = layoutManager
        rvTag.adapter = PopularTagsAdapter(mContext, tagList)

        videoCategoryAdapter.onCategorySelectedListener = { item, isChecked ->
            if (isChecked)
                alSelectedCategory.add(item.categoryId)
            else
                alSelectedCategory.remove(item.categoryId)
        }
    }
}