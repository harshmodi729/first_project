package com.hmatter.first_project.ui.fragment.main

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.PopularClassesAdapter
import com.hmatter.first_project.adapter.PopularTagsAdapter
import com.hmatter.first_project.adapter.VideoCategoryAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.viewmodel.HomeViewModel
import com.hmatter.first_project.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.lay_no_data.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val tagList: ArrayList<String> = ArrayList()
    private val alSelectedCategory = ArrayList<Int>()
    private lateinit var videoCategoryAdapter: VideoCategoryAdapter
    private lateinit var popularTagsAdapter: PopularTagsAdapter
    private lateinit var popularClassesAdapter: PopularClassesAdapter

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
        searchViewModel.videoCategoryLiveData.observe(
            viewLifecycleOwner,
            Observer {
                hideProgressDialog(ivDialogBg)
                when (it) {
                    is BaseResult.Success -> {
                        videoCategoryAdapter.addData(it.item)
                        rvVideoCategory.visibility = View.GONE
                        rvSearchResult.visibility = View.GONE
                    }
                    is BaseResult.Error -> {
                        mContext.makeToast(it.errorMessage)
                    }
                }
            })

        val layoutManager = LinearLayoutManager(mContext)
        popularTagsAdapter = PopularTagsAdapter(mContext)
        rvTag.layoutManager = layoutManager
        rvTag.adapter = popularTagsAdapter

        searchViewModel.getPopularCategoryList().apply {
            laySearch.post {
                showProgressDialog(laySearch, ivDialogBg)
            }
        }
        searchViewModel.alPopularCategoryItem.observe(
            viewLifecycleOwner,
            Observer {
                hideProgressDialog(ivDialogBg)
                when (it) {
                    is BaseResult.Success -> {
                        popularTagsAdapter.addData(it.item)
                        rvTag.visibility = View.VISIBLE
                    }
                    is BaseResult.Error -> {
                        mContext.makeToast(it.errorMessage)
                    }
                }
            }
        )

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
        popularClassesAdapter = PopularClassesAdapter(mContext)
        rvSearchResult.layoutManager = LinearLayoutManager(mContext)
        rvSearchResult.adapter = popularClassesAdapter
        rvSearchResult.isNestedScrollingEnabled = false

        popularTagsAdapter.onTagItemClickListener = { item, position ->
            edSearch.setText(item)
            rvVideoCategory.visibility = View.GONE
            tvTagView.visibility = View.GONE
            rvTag.visibility = View.GONE
            val homeViewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]
            homeViewModel.getPopularClasses().apply {
                laySearch.post {
                    showProgressDialog(laySearch, ivDialogBg)
                }
            }
            homeViewModel.popularClassesLiveData.observe(viewLifecycleOwner, Observer {
                hideProgressDialog(ivDialogBg)
                when (it) {
                    is BaseResult.Success -> {
                        rvSearchResult.visibility = View.VISIBLE
                        tvNoData.visibility = View.GONE
                        tvTotalResult.visibility = View.VISIBLE
                        rvTag.visibility = View.GONE
                        tvTagView.visibility = View.GONE
                        tvTotalResult.text = String.format(
                            mContext.getString(R.string.search_result_text),
                            it.item.size.toString()
                        )
                        popularClassesAdapter.addData(it.item)
                    }
                    is BaseResult.Error -> {
                        rvSearchResult.visibility = View.GONE
                        tvNoData.visibility = View.VISIBLE
                        mContext.makeToast(it.errorMessage)
                    }
                }
            })
        }

        videoCategoryAdapter.onCategorySelectedListener = { item, isChecked ->
            if (isChecked)
                alSelectedCategory.add(item.categoryId)
            else
                alSelectedCategory.remove(item.categoryId)
        }
    }
}