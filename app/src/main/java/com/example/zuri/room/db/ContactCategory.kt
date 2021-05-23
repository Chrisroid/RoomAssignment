package com.example.zuri.room.db

import java.io.Serializable

data class ContactCategory(val name: String, val contacts: MutableList<Contact> = mutableListOf()):Serializable
