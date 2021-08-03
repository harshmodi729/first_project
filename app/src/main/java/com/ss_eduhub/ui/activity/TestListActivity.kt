package com.ss_eduhub.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.databinding.ActivityTestListBinding
import com.ss_eduhub.viewmodel.TestListViewModel
import java.lang.ref.WeakReference

class TestListActivity : BaseActivity() {

    private lateinit var viewModel: TestListViewModel
    private lateinit var binding: ActivityTestListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this)[TestListViewModel::class.java]
        viewModel.setBinding(WeakReference(this), binding, intent)
    }
}