package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBUserTable:Table<DBUserEntity>("Users2"){
    val id= int("id").primaryKey().bindTo{it.id}
    var name= varchar("name").bindTo { it.name}
    var email= varchar("email").bindTo { it.email}
    var m_number=varchar("m_number").bindTo { it.m_number}
    val g_number=int("g_number").bindTo { it.g_number}
}

interface DBUserEntity: Entity<DBUserEntity> {

    companion object : Entity.Factory<DBUserEntity>()

    val id: Int
    var name: String
    var email: String
    var m_number:String
    val g_number:Int

}