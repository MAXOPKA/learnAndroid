package com.example.learnandroid.ui.lists.transactions

import android.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.models.TransactionModel
import kotlinx.android.synthetic.main.fragment_transactions_list_item.view.*


class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dateTextView: TextView = itemView.dateText
    val nameTextView: TextView = itemView.nameText
    val amountTextView: TextView = itemView.amountText
}