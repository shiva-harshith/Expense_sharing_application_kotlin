package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.GroupInfo
import com.example.entities.GroupInfoDraft

class MySqlGroupInfoRepository:GroupInfoRepository {
    val database = DatabaseManager()


    override fun addGroup(draft: GroupInfoDraft): Int {
        return database.addGroup(draft)
    }


}