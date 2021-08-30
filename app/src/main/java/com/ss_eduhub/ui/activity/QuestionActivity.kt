package com.ss_eduhub.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.databinding.ActivityQuestionBinding
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.model.QuestionItem
import com.ss_eduhub.viewmodel.QuestionViewModel
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class QuestionActivity : BaseActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        viewModel.setBinding(WeakReference(this), binding, intent)
        viewModel.getQuestionList().apply {
            binding.layQuestion.post {
                showProgressDialog(binding.layQuestion, binding.ivDialogBg)
            }
        }
        viewModel.getLiveData().observe(this, this::consumeResponse)
        countDownTimer = object : CountDownTimer(QuestionViewModel.HOUR, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = String.format(
                    "%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
            }

            override fun onFinish() {
                Log.e("Timer", "Done")
            }
        }
        countDownTimer.start()
    }

    private fun consumeResponse(response: BaseResult<ArrayList<QuestionItem>>) {
        hideProgressDialog(binding.ivDialogBg)
        when (response) {
            is BaseResult.Success -> {
            }
            is BaseResult.Error -> {
                makeToastForServerError(response)
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        countDownTimer.start()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        countDownTimer.cancel()
//    }
}