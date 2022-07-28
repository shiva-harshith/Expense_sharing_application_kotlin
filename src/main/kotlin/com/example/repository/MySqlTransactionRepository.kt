package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.Transaction
import com.example.entities.TransactionDraft
import com.example.entities.User

class MySqlTransactionRepository:TransactionRepository {
    val database = DatabaseManager()

    override fun getAllTransactions(): List<Transaction> {
        /*return database.getAllTransactions()
            .map{ Transaction(it.id_src,it.id_dest,it.amount) }*/
        return database.getAllTransactions()
    }

    override fun getTransaction(id_src: Int, id_dest: Int): Transaction? {
        return database.getTransaction(id_src,id_dest)
            /*?.let {
                Transaction(it.id_src, it.id_dest, it.amount)
            }*/
    }

    override fun addTransaction(draft: TransactionDraft): Transaction {
        return database.addTransaction(draft)
    }

    override fun updateTransaction(id_src: Int, id_dest: Int, draft: TransactionDraft): Boolean {
        return database.updateTransaction(id_src,id_dest,draft)
    }
}