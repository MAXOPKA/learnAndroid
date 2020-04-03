package com.example.learnandroid.ui.lists.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.R
import com.example.learnandroid.models.UserModel
import com.example.learnandroid.ui.lists.transactions.TransactionViewHolder

class UsersListAdapter(val users: List<UserModel>, val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_users_list_item, parent, false)

        return UserViewHolder(itemView)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val name = users[position].name
        val id = users[position].id

        holder.bind(users[position], itemClickListener)
        holder.nameTextView.text = "$name(ID $id)"
    }
}

interface OnItemClickListener{
    fun onItemClicked(user: UserModel)
}