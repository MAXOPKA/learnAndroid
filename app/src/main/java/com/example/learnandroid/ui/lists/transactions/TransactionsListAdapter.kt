package com.example.learnandroid.ui.lists.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.R
import com.example.learnandroid.models.TransactionModel

class TransactionsListAdapter(private val transactions: Array<TransactionModel>) :
    RecyclerView.Adapter<TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransactionViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_transactions_list_item, parent, false)

        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.dateTextView.text = transactions[position].date
        holder.nameTextView.text = transactions[position].username
        holder.amountTextView.text = transactions[position].amount.toString()
    }

    override fun getItemCount() = transactions.size
}