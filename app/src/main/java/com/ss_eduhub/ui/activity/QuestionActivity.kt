package com.ss_eduhub.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.databinding.ActivityQuestionBinding
import com.ss_eduhub.viewmodel.QuestionViewModel
import java.lang.ref.WeakReference

class QuestionActivity : BaseActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        viewModel.setBinding(WeakReference(this), binding, intent)
    }
}