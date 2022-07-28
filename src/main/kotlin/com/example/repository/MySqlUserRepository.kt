package com.example.repository

import com.example.database.DBUserEntity
import com.example.database.DatabaseManager
import com.example.entities.User
import com.example.entities.UserDraft

class MySqlUserRepository:UserRepository {

    val database = DatabaseManager()

    override fun getAllUsers(): List<User> {
        return database.getAllUsers()
            //.map{User(it.id,it.name,it.email,it.m_number,it.g_number)}
    }

    override fun getUser(id: Int): User? {
        /*return database.getUser(id)
            ?.let {
                User(it.id, it.name, it.email, it.m_number, it.g_number)
            }*/
        return database.getUser(id)

    }

    override fun addUser(draft: UserDraft): User {
        return database.addUser(draft)
    }

    override fun removeUser(id: Int): Boolean {
        return database.removeUser(id)
    }

    override fun updateUser(id: Int, draft: UserDraft): Boolean {
        return database.updateUser(id,draft)
    }

   /* override fun getGroup(g_number: Int?): DBUserEntity? {

    }*/
}