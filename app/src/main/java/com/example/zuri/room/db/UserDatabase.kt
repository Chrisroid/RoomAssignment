package com.example.zuri.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object{
        @Volatile private lateinit var instance: UserDatabase
        private val LOCK = Any()

        fun invoke(context: Context): UserDatabase {
            if(!this::instance.isInitialized)
                synchronized(LOCK){ instance = buildDatabse(context)}

            return instance
        }

        private fun buildDatabse(context: Context) = Room.databaseBuilder(context, UserDatabase::class.java, "users.db").build()
    }
}