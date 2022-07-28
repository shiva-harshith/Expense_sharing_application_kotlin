package com.example.services

import com.example.database.DBTransactionEntity
import com.example.database.DBTransactionTable
import com.example.entities.Transaction
import com.example.entities.TransactionDraft
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class TransactionServices {

    val ktormDatabase= Database.connect(
        url = "jdbc:mysql://localhost:3306/Expense?autoReconnect=true&useSSL=false",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="Shiva_Harshith01"
    )

    fun getAllTransactions(): List<DBTransactionEntity> {
        return ktormDatabase.sequenceOf(DBTransactionTable).toList()
    }
    fun getTransaction(id_src: Int,id_dest:Int): DBTransactionEntity? {
        return ktormDatabase.sequenceOf(DBTransactionTable)
            .firstOrNull { (it.id_src eq id_src) and  (it.id_dest eq id_dest)}
    }
    fun addTransaction(draft: TransactionDraft): Transaction {
        val insertedId = ktormDatabase.insert(DBTransactionTable) {
            set(DBTransactionTable.id_src, draft.id_src)
            set(DBTransactionTable.id_dest, draft.id_dest)
            set(DBTransactionTable.amount, draft.amount)
            //set(DBTransactionTable.g_number, draft.g_number)
        } as Int

        return Transaction(draft.id_src, draft.id_dest,draft.amount)  //doubt
    }
    fun updateTransaction(id_src: Int, id_dest:Int,draft: TransactionDraft): Boolean {
        var temp_amount=DBTransactionTable.amount + draft.amount
        val updatedRows = ktormDatabase.update(DBTransactionTable) {
            set(DBTransactionTable.amount, (temp_amount))
            where {
                (it.id_src eq id_src) and (it.id_dest eq id_dest)
            }
        }

        return updatedRows > 0
    }
}