package com.example.services

import com.example.database.DBTransactionTable
import com.example.database.DBUserTable
import com.example.entities.SplitTransaction
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.support.mysql.insertOrUpdate

class SplitTransactionServices {
    val ktormDatabase= Database.connect(
        url = "jdbc:mysql://localhost:3306/Expense?autoReconnect=true&useSSL=false",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="Shiva_Harshith01"
    )


    fun groupNameQuery(g_number:Int): Query{
        return ktormDatabase.from(DBUserTable).select(DBUserTable.id).where {
            DBUserTable.g_number eq g_number
        }
    }
    fun split(d_id:Int?,paying_id:Int,amountPerHead:Int): Unit{
        ktormDatabase.insertOrUpdate(DBTransactionTable) {
            set(DBTransactionTable.id_src, d_id)
            set(DBTransactionTable.id_dest, paying_id)
            set(DBTransactionTable.amount, amountPerHead)
            onDuplicateKey {
                set(DBTransactionTable.amount, DBTransactionTable.amount + amountPerHead)
            }
        }
    }
}