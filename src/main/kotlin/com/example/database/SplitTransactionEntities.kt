package com.example.database



import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBSplitTransactionTable:Table<DBSplitTransactionEntity>("SplitTransactions"){
    val id= int("id").bindTo{it.id}
    var amount= int("amount").bindTo { it.amount}
    val g_number=int("g_number").bindTo { it.g_number}
}

interface DBSplitTransactionEntity: Entity<DBSplitTransactionEntity> {

    companion object : Entity.Factory<DBSplitTransactionEntity>()

    val id: Int
    var amount: Int
    var g_number: Int

}