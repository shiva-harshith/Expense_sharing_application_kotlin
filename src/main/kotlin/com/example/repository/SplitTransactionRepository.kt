package com.example.repository

import com.example.entities.SplitTransaction

interface SplitTransactionRepository {
    fun addSplitTransaction(draft: SplitTransaction): Int
}