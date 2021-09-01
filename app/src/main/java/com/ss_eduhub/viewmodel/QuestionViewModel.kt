package com.ss_eduhub.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ss_eduhub.R
import com.ss_eduhub.adapter.AnswerAdapter
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.base.BaseViewModel
import com.ss_eduhub.common.Constants
import com.ss_eduhub.databinding.ActivityQuestionBinding
import com.ss_eduhub.extension.hideKeyboard
import com.ss_eduhub.model.Answers
import com.ss_eduhub.model.CourseTestItem
import com.ss_eduhub.model.QuestionItem
import com.ss_eduhub.model.TestListItem
import com.ss_eduhub.ui.activity.QuestionActivity
import com.ss_eduhub.ui.activity.ResultActivity
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class QuestionViewModel : BaseViewModel(), AnswerAdapter.OnCardClickListener {

    companion object {
        const val HOUR: Long = (60 * 1000) * 60
    }

    private lateinit var activity: WeakReference<QuestionActivity>
    private lateinit var binding: ActivityQuestionBinding
    private var testListItem: TestListItem? = null
    private var courseTestItem: CourseTestItem? = null
    private var questionList = ArrayList<QuestionItem>()
    private var cnt = 0
    private var rightCnt = 0
    private var wrongCnt = 0
    private lateinit var adapter: AnswerAdapter
    private var liveData = MutableLiveData<BaseResult<ArrayList<QuestionItem>>>()

    @SuppressLint("SetTextI18n")
    fun setBinding(
        activity: WeakReference<QuestionActivity>,
        binding: ActivityQuestionBinding,
        intent: Intent
    ) {
        this.activity = activity
        this.binding = binding

        intent.extras?.let {
            testListItem =
                intent.getSerializableExtra(Constants.QUESTION_ITEM) as TestListItem
            courseTestItem =
                intent.getSerializableExtra(Constants.COURSE_TEST_ITEM) as CourseTestItem
            binding.toolbar.tvToolbarTitle.text = testListItem!!.testName
        } ?: kotlin.run {
            binding.toolbar.tvToolbarTitle.text = "Select Test"
        }
        binding.toolbar.btnToolbarBack.setOnClickListener {
            activity.get()!!.onBackPressed()
        }

        binding.tvSubject.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                activity.get()!!,
                R.drawable.layout_bg_dropdown
            )
        )
        binding.tvSubject.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textView: TextView,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    activity.get()!!.hideKeyboard(binding.tvSubject)
                    return true
                }
                return false
            }
        })

        val data = arrayOf("Physics", "Chemistry", "Biology", "Maths", "Hindi")
        val dropdownAdapter =
            activity.get()?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    data
                )
            }
        binding.tvSubject.setAdapter(dropdownAdapter)

        binding.tvRightAns.text = "$rightCnt"
        binding.tvWrongAns.text = "$wrongCnt"
        adapter = AnswerAdapter(this)
        binding.rvAnswers.adapter = adapter

        binding.btnBack.setOnClickListener {
            if (cnt > 0) {
                cnt--
                binding.tvQuestion.text = questionList[cnt].question
                binding.tvCnt.text = "${cnt + 1}"
                adapter.addData(questionList[cnt])
            }
        }
        binding.btnNext.setOnClickListener {
            if (cnt < questionList.size - 1) {
                cnt++
                binding.tvQuestion.text = questionList[cnt].question
                binding.tvCnt.text = "${cnt + 1}"
                adapter.addData(questionList[cnt])
                if (cnt == questionList.size - 1)
                    binding.btnNext.text = "Finish"
            } else {
                binding.btnNext.text = "Finish"
                activity.get()!!.startActivity(
                    Intent(activity.get()!!, ResultActivity::class.java)
                        .putExtra(Constants.QUESTION_ITEM, testListItem)
                        .putExtra(Constants.QUESTION_LIST, questionList)
                )
                activity.get()!!.finish()
            }
        }
    }

    fun getLiveData(): MutableLiveData<BaseResult<ArrayList<QuestionItem>>> {
        return liveData
    }

    @SuppressLint("SetTextI18n")
    fun getQuestionList() {
        viewModelScope.launch {
            try {
                val response = getApiServiceManager().getQuestionList(courseTestItem?.id!!)
                if (response.success) {
                    response.data?.let {
                        liveData.value = BaseResult.Success(it)
                        it.forEach { questionItemX ->
                            run {
                                val options = ArrayList<Answers>()
                                questionItemX.options.forEach { option ->
                                    val answerX = Answers(option)
                                    options.add(answerX)
                                }
                                questionItemX.answers = options
                            }
                        }
                        questionList = it
                        if (cnt == questionList.size - 1)
                            binding.btnNext.text = "Finish"
                        binding.tvQuestion.text = questionList[cnt].question
                        binding.tvCnt.text = "${cnt + 1}"
                        adapter.addData(questionList[cnt])
                    } ?: kotlin.run {
                        liveData.value =
                            BaseResult.Error(IllegalStateException("Oops something went wrong."))
                    }
                } else {
                    liveData.value =
                        BaseResult.Error(IllegalStateException(), response.message)
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                liveData.value = BaseResult.Error(exception)
            }
        }
    }

    /*private fun getList() {
        var answers = ArrayList<Answer>()
        answers.add(Answer("6", "1"))
        answers.add(Answer("9", "2"))
        answers.add(Answer("11", "3"))
        answers.add(Answer("5", "4"))
        var questionItem = QuestionItem(
            "If the number can be divided by the numbers 2 and 3, then that number is divisible by _____.",
            answers,
            "1"
        )
        questionList.add(questionItem)

        answers = ArrayList()
        answers.add(Answer("Concurrent lines", "1"))
        answers.add(Answer("Straight lines", "2"))
        answers.add(Answer("Parallel lines", "3"))
        answers.add(Answer("Line segments", "4"))
        questionItem = QuestionItem(
            "Lines in the same plane which do not intersect each other are _____.", answers, "3"
        )
        questionList.add(questionItem)

        answers = ArrayList()
        answers.add(Answer("24 cm", "1"))
        answers.add(Answer("12 cm", "2"))
        answers.add(Answer("48 cm", "3"))
        answers.add(Answer("36 cm", "4"))
        questionItem = QuestionItem(
            "The perimeter of a square with sides of 12 cm is", answers, "3"
        )
        questionList.add(questionItem)

        answers = ArrayList()
        answers.add(Answer("No", "1"))
        answers.add(Answer("Yes", "2"))
        questionItem = QuestionItem(
            "Sentence is correct or not: \"The number 5 is neither a prime number nor a composite number.\"",
            answers,
            "1"
        )
        questionList.add(questionItem)
    }*/

    override fun onAnswerSelected(item: Answers, position: Int) {
        activity.get()!!.showProgressDialog(binding.layQuestion, binding.ivDialogBg)
        Handler(Looper.getMainLooper()).postDelayed({
            activity.get()!!.hideProgressDialog(binding.ivDialogBg)
            item.isCorrect = (position + 1).toString() == questionList[cnt].ans

            if (!questionList[cnt].isAttempted) {
                if (item.isCorrect) {
                    rightCnt++
                    questionList[cnt].isAnswered = true
                } else wrongCnt++
            }
            binding.tvRightAns.text = "$rightCnt"
            binding.tvWrongAns.text = "$wrongCnt"

            item.isAnswered = true
            questionList[cnt].isAttempted = true
            adapter.notifyItemChanged(position)
        }, 1500)
    }
}

