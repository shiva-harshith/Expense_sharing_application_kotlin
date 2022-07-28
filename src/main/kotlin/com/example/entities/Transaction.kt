package com.example.entities

data class Transaction(
    val id_src: Int,
    val id_dest: Int,
    var amount: Int,
    //var g_number: Int
)
