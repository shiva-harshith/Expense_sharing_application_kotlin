package com.example.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBGroupInfoTable:Table<DBGroupInfoEntity>("GroupInfo"){

    var g_number= int("g_number").primaryKey().bindTo { it.g_number}

}
interface DBGroupInfoEntity: Entity<DBGroupInfoEntity> {

    companion object : Entity.Factory<DBGroupInfoEntity>()

    var g_number: Int
}