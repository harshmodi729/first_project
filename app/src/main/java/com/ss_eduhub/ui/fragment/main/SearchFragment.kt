package com.ss_eduhub.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ss_eduhub.R
import com.ss_eduhub.adapter.PopularClassesAdapter
import com.ss_eduhub.adapter.PopularTagsAdapter
import com.ss_eduhub.adapter.VideoCategoryAdapter
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.common.Constants
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.ui.activity.ClassActivity
import com.ss_eduhub.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.lay_no_data.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SearchFragment : BaseFragment(R.layout.fragment_search) {
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
                        mContext.makeToastForServerError(it)
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
                        mContext.makeToastForServerError(it)
                    }
                }
            }
        )

        val cooking = resources.getStringArray(R.array.cooking_array)
        val adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, cooking) }
        edSearch.setAdapter(adapter)
        edSearch.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.layout_bg_dropdown
            )
        )
        edSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textView: TextView,
                actionId: Int,
                event: KeyEvent
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    return true
                }
                return false
            }
        })
        popularClassesAdapter = PopularClassesAdapter(mContext)
        rvSearchResult.layoutManager = LinearLayoutManager(mContext)
        rvSearchResult.adapter = popularClassesAdapter
        rvSearchResult.isNestedScrollingEnabled = false

        popularTagsAdapter.onTagItemClickListener = { item, position ->
            edSearch.setText(item)
            rvVideoCategory.visibility = View.GONE
            tvTagView.visibility = View.GONE
            rvTag.visibility = View.GONE

            searchViewModel.getCatFilterData(position).apply {
                laySearch.post {
                    showProgressDialog(laySearch, ivDialogBg)
                }
            }
            searchViewModel.alCategoryWiseFilterItem.observe(viewLifecycleOwner, Observer {
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
                        mContext.makeToastForServerError(it)
                    }
                }
            })
        }

        popularClassesAdapter.onCardClickListener = { item, _ ->
            mContext.startActivity(
                Intent(mContext, ClassActivity::class.java).putExtra(Constants.CLASS_ITEM, item)
            )
        }

        videoCategoryAdapter.onCategorySelectedListener = { item, isChecked ->
            if (isChecked)
                alSelectedCategory.add(item.categoryId)
            else
                alSelectedCategory.remove(item.categoryId)
        }
    }
}