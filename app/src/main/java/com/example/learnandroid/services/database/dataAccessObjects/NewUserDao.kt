package com.example.learnandroid.services.database.dataAccessObjects

import androidx.room.*
import com.example.learnandroid.services.database.entities.NewUser

@Dao
interface NewUserDao {
    @Query("SELECT * FROM newuser LIMIT 1")
    fun getFirst(): NewUser?

    @Update
    fun updateFirst(employee: NewUser?)
}