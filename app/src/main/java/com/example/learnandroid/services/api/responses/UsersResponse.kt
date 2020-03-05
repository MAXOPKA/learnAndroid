package com.example.learnandroid.services.api.responses

import com.example.learnandroid.models.UserModel

data class UsersResponse(val users: List<User>){
}

data class User(val id: Int, val name: String){
    fun toModel(): UserModel {
        return UserModel(id, name)
    }
}