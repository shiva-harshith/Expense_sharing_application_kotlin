package com.example.repository

import com.example.database.DBUserEntity
import com.example.entities.User
import com.example.entities.UserDraft

interface UserRepository {
    fun getAllUsers(): List<User>

    fun getUser(id: Int): User?

    fun addUser(draft: UserDraft): User

    fun removeUser(id: Int): Boolean

    fun updateUser(id: Int, draft: UserDraft): Boolean

   // fun getGroup(g_number:Int?): DBUserEntity?

}