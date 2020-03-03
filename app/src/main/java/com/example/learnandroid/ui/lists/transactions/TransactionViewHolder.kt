package com.example.learnandroid.ui.lists.transactions

import android.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.models.TransactionModel
import kotlinx.android.synthetic.main.fragment_transactions_list_item.view.*


class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dateTextView: TextView
    val nameTextView: TextView
    val amountTextView: TextView

    init {
        dateTextView = itemView.dateText
        nameTextView = itemView.nameText
        amountTextView = itemView.amountText
    }

    fun bind(transaction: TransactionModel) {
        dateTextView.text = transaction.date
        nameTextView.text = transaction.username
        amountTextView.text = transaction.amount.toString()
    }
}