package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.PopularTagsAdapter
import com.hmatter.first_project.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    val tagList: ArrayList<String> = ArrayList()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.search)
        tvToolbarTitle.visibility = View.GONE
        ivSearchLayout.visibility = View.VISIBLE

        val cooking = resources.getStringArray(R.array.cooking_array)
        // Create the adapter and set it to the AutoCompleteTextView
        val adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, cooking) }
        ivSearchLayout.setAdapter(adapter)
        ivSearchLayout.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.layout_bg_dropdown
            )
        )

        tagList.add("Business & Management")
        tagList.add("Creative Art & Media")
        tagList.add("Health & Psycology")
        tagList.add("History")
        tagList.add("Language and Culture")
        tagList.add("Science Engineering & Maths")
        tagList.add("Study Skills")
        tagList.add("Tech & Coding")

        var layoutManager = LinearLayoutManager(activity)
        rcvTag.layoutManager = layoutManager
        rcvTag.adapter = activity?.let { PopularTagsAdapter(it, tagList) }
    }
}