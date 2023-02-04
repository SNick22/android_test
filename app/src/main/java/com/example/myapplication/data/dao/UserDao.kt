package com.example.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = ABORT)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): User

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User

    @Query("UPDATE user SET email = :newEmail WHERE email = :oldEmail")
    fun updateEmail(newEmail: String, oldEmail: String)

    @Update
    fun update(user: User)
}