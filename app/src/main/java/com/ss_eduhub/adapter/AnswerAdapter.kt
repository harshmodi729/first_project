package com.ss_eduhub.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ss_eduhub.databinding.LayAnswerItemBinding
import com.ss_eduhub.model.Answers
import com.ss_eduhub.model.QuestionItem

class AnswerAdapter(private val onCardClickListener: OnCardClickListener) :
    RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {

    private lateinit var questionItem: QuestionItem
    private var selectedPosition = -1
    private var list = ArrayList<Answers>()

    inner class ViewHolder(val binding: LayAnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCnt: AppCompatTextView = binding.tvCnt
        val tvAnswer: CheckedTextView = binding.tvAnswer
        val cvAnswer = binding.cvAnswer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerAdapter.ViewHolder {
        return ViewHolder(
            LayAnswerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: AnswerAdapter.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val item = questionItem.answers[position]
        holder.tvCnt.text = "${position + 1}"
        holder.tvAnswer.text = item.answer
        holder.tvAnswer.isChecked = item.isChecked
        holder.cvAnswer.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        if (questionItem.isAttempted && item.isAnswered)
            if (item.isCorrect) {
                holder.cvAnswer.setCardBackgroundColor(Color.parseColor("#8CEA7C"))
            } else {
                holder.cvAnswer.setCardBackgroundColor(Color.parseColor("#ED7070"))
            }

        holder.tvAnswer.setOnClickListener {
            if (!item.isAnswered) {
                if (selectedPosition != -1) {
                    item.isChecked = false
                }
                selectedPosition = position
                holder.tvAnswer.isChecked = true
                item.isChecked = true
                onCardClickListener.onAnswerSelected(item, position)
            }
        }
    }

    override fun getItemCount() = list.size

    interface OnCardClickListener {
        fun onAnswerSelected(item: Answers, position: Int)
    }

    fun addData(questionItem: QuestionItem) {
        this.questionItem = questionItem
        list = questionItem.answers
        notifyDataSetChanged()
    }
}