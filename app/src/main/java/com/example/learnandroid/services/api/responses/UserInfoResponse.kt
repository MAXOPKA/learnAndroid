package com.example.learnandroid.services.api.responses

data class UserInfoResponse(val user_info_token: UserInfoToken){
}

data class UserInfoToken(val id: Int, val name: String, val email: String, val balance: Double) {
}