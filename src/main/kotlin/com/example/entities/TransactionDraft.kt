package com.example.entities

data class TransactionDraft(
    val id_src: Int,
    val id_dest: Int,
    var amount: Int,
    //var g_number:Int
)
