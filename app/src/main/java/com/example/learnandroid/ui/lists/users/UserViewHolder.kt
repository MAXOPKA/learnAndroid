package com.example.learnandroid.ui.lists.users

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.models.UserModel
import kotlinx.android.synthetic.main.fragment_transactions_list_item.view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.nameText

    fun bind(user: UserModel, clickListener: OnItemClickListener)
    {
        nameTextView.text = user.name

        itemView.setOnClickListener {
            clickListener.onItemClicked(user)
        }
    }
}