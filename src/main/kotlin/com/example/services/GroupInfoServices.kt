package com.example.services

import com.example.database.DBGroupInfoTable
import com.example.entities.GroupInfoDraft
import org.ktorm.database.Database
import org.ktorm.dsl.insert

class GroupInfoServices {
    val ktormDatabase= Database.connect(
        url = "jdbc:mysql://localhost:3306/Expense?autoReconnect=true&useSSL=false",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="Shiva_Harshith01"
    )

    fun addGroup(draft: GroupInfoDraft): Int {
        ktormDatabase.insert(DBGroupInfoTable){
            set(DBGroupInfoTable.g_number,draft.g_number)
        }
        return 0
    }
}