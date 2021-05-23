package com.example.zuri.room.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromContact(contact: Contact?): String?{
        return contact?.let{
            gson.toJson(it)
        }
    }

    @TypeConverter
    fun toContact(value: String?): Contact?{
        return value?.let{
            gson.fromJson(it, Contact::class.java)
        }
    }

    @TypeConverter
    fun fromCategory(category: ContactCategory?): String?{
        return category?.let{
            gson.toJson(it)
        }
    }

    @TypeConverter
    fun toCategory(value: String?): ContactCategory?{
        return value?.let{
            gson.fromJson(it, ContactCategory::class.java)
        }
    }

    @TypeConverter
    fun fromCategories(categories: List<ContactCategory>?): String?{
        return categories?.let{
            gson.toJson(it)
        }
    }

    @TypeConverter
    fun toCategories(value: String?): List<ContactCategory>?{
        return value?.let{
            val type = object : TypeToken<List<ContactCategory>>() {}.type
            gson.fromJson<List<ContactCategory>>(it, type)
        }
    }
}