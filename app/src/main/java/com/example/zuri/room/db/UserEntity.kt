package com.example.zuri.room.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class UserEntity(
    @ColumnInfo val username: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val password: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
): Serializable{
    @ColumnInfo var contactCategories: MutableList<ContactCategory> = mutableListOf()

    companion object{
        const val USER = "USER"
    }
}
