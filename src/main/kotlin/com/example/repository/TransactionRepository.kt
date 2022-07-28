package com.example.repository

import com.example.entities.Transaction
import com.example.entities.TransactionDraft

interface TransactionRepository {
    fun getAllTransactions(): List<Transaction>

    fun getTransaction(id_src: Int,id_dest:Int): Transaction?

    fun addTransaction(draft: TransactionDraft): Transaction

    //fun removeTransaction(id: Int): Boolean

    fun updateTransaction(id_src: Int,id_dest:Int, draft: TransactionDraft): Boolean
}