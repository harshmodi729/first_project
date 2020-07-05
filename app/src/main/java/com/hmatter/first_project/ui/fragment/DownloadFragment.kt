package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmatter.first_project.R
import com.hmatter.first_project.adapter.DownloadAdapter
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.callback.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_download.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class DownloadFragment : BaseFragment(R.layout.fragment_download) {
    private val downloadAdapter = DownloadAdapter((1..5).map { "Item: $it" }.toMutableList())
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.download)

        rcvDownloads.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        rcvDownloads.layoutManager = LinearLayoutManager(activity)
        rcvDownloads.adapter = downloadAdapter

        val swipeHandler = object : SwipeToDeleteCallback(this!!.requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rcvDownloads.adapter as DownloadAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rcvDownloads)
    }
}