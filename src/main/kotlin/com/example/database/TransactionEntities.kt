package com.example.database

import com.example.database.DBUserTable.bindTo
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBTransactionTable:Table<DBTransactionEntity>("transactions"){
    val id_src= int("id_src").primaryKey().bindTo{it.id_src}
    var id_dest= int("id_dest").primaryKey().bindTo { it.id_dest}
    var amount= int("amount").bindTo { it.amount}
    //var g_number=int("g_number").bindTo { it.g_number }
}

interface DBTransactionEntity: Entity<DBTransactionEntity> {

    companion object : Entity.Factory<DBTransactionEntity>()

    val id_src: Int
    val id_dest: Int
    var amount: Int
    //var g_number:Int

}