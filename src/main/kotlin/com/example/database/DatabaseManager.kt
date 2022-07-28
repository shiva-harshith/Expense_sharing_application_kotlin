package com.example.database

import com.example.entities.*
import com.example.services.GroupInfoServices
import com.example.services.SplitTransactionServices
import com.example.services.TransactionServices
import com.example.services.UserServices


class DatabaseManager {

    //User endpoints

    val userServices=UserServices()

    fun getAllUsers(): List<User> {
        return userServices.getAllUsers().map{User(it.id,it.name,it.email,it.m_number,it.g_number)}

    }

    fun getUser(id: Int): User? {
        return userServices.getUser(id)
            ?.let {
                User(it.id, it.name, it.email, it.m_number, it.g_number)
            }
    }

    fun addUser(draft: UserDraft): User {
        return userServices.addUser(draft)
    }
    fun updateUser(id: Int, draft: UserDraft): Boolean {
        return userServices.updateUser(id,draft)
    }
    fun removeUser(id: Int): Boolean {
        return userServices.removeUser(id)
    }



    //Transaction endpoints



    val transactionServices=TransactionServices()

    fun getAllTransactions(): List<Transaction> {
         return transactionServices.getAllTransactions()
            .map{ Transaction(it.id_src,it.id_dest,it.amount)}
    }


    fun getTransaction(id_src: Int,id_dest:Int): Transaction? {
        return transactionServices.getTransaction(id_src,id_dest)
            ?.let {
                Transaction(it.id_src, it.id_dest, it.amount)
            }
    }


    fun addTransaction(draft: TransactionDraft): Transaction {
        return transactionServices.addTransaction(draft)  //doubt
    }
    fun updateTransaction(id_src: Int, id_dest:Int,draft: TransactionDraft): Boolean {
        return transactionServices.updateTransaction(id_src,id_dest,draft)
    }

    //SplitTransaction  endpoints

    val splitTransactionServices= SplitTransactionServices()



     fun addSplitTransaction(draft: SplitTransaction): Int {
         val g_number = draft.g_number
         val paying_id = draft.id
         val groupNameQuery = splitTransactionServices.groupNameQuery(g_number)
         val totalMembers = groupNameQuery.totalRecords
         val amountPerHead: Int = draft.amount / totalMembers
         for (row in groupNameQuery) {
             val d_id = row[DBUserTable.id]
             if (d_id == paying_id) continue
             splitTransactionServices.split(d_id,paying_id,amountPerHead)

         }

      return 0
     }
  //GroupInfo endpoints

    val groupInfoServices=GroupInfoServices()

   fun addGroup(draft: GroupInfoDraft): Int {
      return groupInfoServices.addGroup(draft)}

}