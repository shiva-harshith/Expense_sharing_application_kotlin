package com.example.services

import com.example.database.DBUserEntity
import com.example.database.DBUserTable
import com.example.entities.User
import com.example.entities.UserDraft
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class UserServices {
    val ktormDatabase= Database.connect(
        url = "jdbc:mysql://localhost:3306/Expense?autoReconnect=true&useSSL=false",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="Shiva_Harshith01"
    )

    fun getAllUsers(): List<DBUserEntity> {
        return ktormDatabase.sequenceOf(DBUserTable).toList()
    }
    fun getUser(id: Int): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.id eq id }
    }
    fun addUser(draft: UserDraft): User {
        val insertedId = ktormDatabase.insertAndGenerateKey(DBUserTable) {
            set(DBUserTable.name, draft.name)
            set(DBUserTable.email, draft.email)
            set(DBUserTable.m_number, draft.m_number)
            set(DBUserTable.g_number, draft.g_number)
        } as Int

        return User(insertedId, draft.name, draft.email,draft.m_number,draft.g_number)
    }
    fun updateUser(id: Int, draft: UserDraft): Boolean {
        val updatedRows = ktormDatabase.update(DBUserTable) {
            set(DBUserTable.name, draft.name)
            set(DBUserTable.email, draft.email)
            set(DBUserTable.m_number, draft.m_number)
            set(DBUserTable.g_number, draft.g_number)
            where {
                it.id eq id
            }
        }

        return updatedRows > 0
    }
    fun removeUser(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBUserTable) {
            it.id eq id
        }
        return deletedRows > 0
    }
}