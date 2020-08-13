package com.sseduhub.edupi.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sseduhub.edupi.R
import com.sseduhub.edupi.adapter.DownloadAdapter
import com.sseduhub.edupi.base.BaseFragment
import com.sseduhub.edupi.common.Constants
import com.sseduhub.edupi.extension.getProgressDialog
import com.sseduhub.edupi.extension.onDialogButtonClick
import kotlinx.android.synthetic.main.fragment_download.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class DownloadFragment : BaseFragment(R.layout.fragment_download) {
    private lateinit var deleteDialog: AlertDialog
    private lateinit var downloadAdapter: DownloadAdapter
    private var deletedPosition = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.download)

        downloadAdapter = DownloadAdapter(mContext)
        rcvDownloads.layoutManager = LinearLayoutManager(mContext)
        rcvDownloads.adapter = downloadAdapter
        downloadAdapter.addData((1..5).map { "Item $it" } as ArrayList<String>)

        downloadAdapter.onDeleteClickListener = { item, position ->
            deletedPosition = position
            val message: String =
                String.format(mContext.getString(R.string.you_want_to_delete), item)
            deleteDialog = mContext.getProgressDialog(
                R.layout.lay_dialog_delete,
                message,
                Constants.DELETE_DIALOG
            )
            deleteDialog.show()
        }

        onDialogButtonClick = {
            if (it) {
                downloadAdapter.removeItem(deletedPosition)
            }
            downloadAdapter.notifyDataSetChanged()
            deletedPosition = -1
            deleteDialog.dismiss()
        }
    }
}