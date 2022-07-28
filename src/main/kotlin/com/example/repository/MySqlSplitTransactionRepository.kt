package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.SplitTransaction

class MySqlSplitTransactionRepository:SplitTransactionRepository {
    val database = DatabaseManager()

    override fun addSplitTransaction(draft: SplitTransaction): Int {
        return database.addSplitTransaction(draft)
    }
}