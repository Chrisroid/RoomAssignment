package com.example.zuri.room.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<UserEntity>>?

    @Query("SELECT * FROM users WHERE username LIKE :username LIMIT 1")
    fun findByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE email LIKE :email LIMIT 1")
    fun findByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)
}