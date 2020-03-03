package com.example.learnandroid.services.api.responses

data class UsersResponse(val users: List<User>){
}

data class User(val id: Int, val name: String){
}