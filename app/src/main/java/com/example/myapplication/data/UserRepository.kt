package com.example.myapplication.data

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {

    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    private val userDao by lazy {
        db.getUserDao()
    }

    suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.save(user)
        }
    }

    suspend fun getUserByEmail(email: String): User =
        withContext(Dispatchers.IO) {
            userDao.getUserByEmail(email)
        }

    suspend fun getUserByEmailAndPassword(email: String, password: String): User =
        withContext(Dispatchers.IO) {
            userDao.getUserByEmailAndPassword(email, password)
        }

    suspend fun updateUserEmail(newEmail: String, oldEmail: String) {
        withContext(Dispatchers.IO) {
            userDao.updateEmail(newEmail, oldEmail)
        }
    }

    suspend fun update(user: User) {
        withContext(Dispatchers.IO) {
            userDao.update(user)
        }
    }

    companion object {
        private const val DATABASE_NAME = "users.db"
    }
}