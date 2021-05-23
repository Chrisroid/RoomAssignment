package com.example.zuri.room.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {
    private val db = UserDatabase.invoke(context)

    suspend fun findUserByEmail(email: String): UserEntity? = withContext(Dispatchers.IO) {
        db.userDao().findByEmail(email)
    }

    fun insertUser(userEntity: UserEntity){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                db.userDao().insertUser(userEntity)
            }
        }
    }

    fun updateUser(userEntity: UserEntity){
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                db.userDao().insertUser(userEntity)
            }
        }
    }
}