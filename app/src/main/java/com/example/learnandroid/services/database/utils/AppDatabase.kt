package com.example.learnandroid.services.database.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learnandroid.services.database.dataAccessObjects.NewUserDao
import com.example.learnandroid.services.database.entities.NewUser


@Database(entities = [NewUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): NewUserDao?
}