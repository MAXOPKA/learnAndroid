package com.example.learnandroid.services.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NewUser {
    @PrimaryKey
    var email: String = ""
    var name: String? = null
    var password: String? = null
}