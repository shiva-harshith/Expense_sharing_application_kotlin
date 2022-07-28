package com.example.repository

import com.example.entities.GroupInfo
import com.example.entities.GroupInfoDraft


interface GroupInfoRepository {
    fun addGroup(draft: GroupInfoDraft): Int
}