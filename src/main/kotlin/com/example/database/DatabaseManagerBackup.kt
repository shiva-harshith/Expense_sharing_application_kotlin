package com.example.database

import com.example.entities.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.support.mysql.insertOrUpdate

class DatabaseManagerBackup {
    val ktormDatabase= Database.connect(
        url = "jdbc:mysql://localhost:3306/Expense?autoReconnect=true&useSSL=false",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="Shiva_Harshith01"
    )
    //User endpoints
    fun getAllUsers(): List<DBUserEntity> {
        return ktormDatabase.sequenceOf(DBUserTable).toList()
    }

    fun getUser(id: Int): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { it.id eq id }
    }

    fun addUser(draft: UserDraft): User {
        val insertedId = ktormDatabase.insertAndGenerateKey(DBUserTable) {
            set(DBUserTable.name, draft.name)
            set(DBUserTable.email, draft.email)
            set(DBUserTable.m_number, draft.m_number)
            set(DBUserTable.g_number, draft.g_number)
        } as Int

        return User(insertedId, draft.name, draft.email,draft.m_number,draft.g_number)
    }
    fun updateUser(id: Int, draft: UserDraft): Boolean {
        val updatedRows = ktormDatabase.update(DBUserTable) {
            set(DBUserTable.name, draft.name)
            set(DBUserTable.email, draft.email)
            set(DBUserTable.m_number, draft.m_number)
            set(DBUserTable.g_number, draft.g_number)
            where {
                it.id eq id
            }
        }

        return updatedRows > 0
    }

    fun removeUser(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBUserTable) {
            it.id eq id
        }
        return deletedRows > 0
    }


    //Transaction endpoints




    fun getAllTransactions(): List<DBTransactionEntity> {
        return ktormDatabase.sequenceOf(DBTransactionTable).toList()
    }


    fun getTransaction(id_src: Int,id_dest:Int): DBTransactionEntity? {
        return ktormDatabase.sequenceOf(DBTransactionTable)
            .firstOrNull { (it.id_src eq id_src) and  (it.id_dest eq id_dest)}
    }


    fun addTransaction(draft: TransactionDraft): Transaction {
        val insertedId = ktormDatabase.insert(DBTransactionTable) {
            set(DBTransactionTable.id_src, draft.id_src)
            set(DBTransactionTable.id_dest, draft.id_dest)
            set(DBTransactionTable.amount, draft.amount)
            //set(DBTransactionTable.g_number, draft.g_number)
        } as Int

        return Transaction(draft.id_src, draft.id_dest,draft.amount)  //doubt
    }
    fun updateTransaction(id_src: Int, id_dest:Int,draft: TransactionDraft): Boolean {
        var temp_amount=DBTransactionTable.amount + draft.amount
        val updatedRows = ktormDatabase.update(DBTransactionTable) {
            set(DBTransactionTable.amount, (temp_amount))
            where {
                (it.id_src eq id_src) and (it.id_dest eq id_dest)
            }
        }

        return updatedRows > 0
    }

    //SplitTransaction  endpoints
    fun getGroup(g_number:Int): DBUserEntity? {
        return ktormDatabase.sequenceOf(DBUserTable)
            .firstOrNull { (it.g_number eq g_number)}
    }

    fun addSplitTransaction(draft: SplitTransaction): Int {
        val g_number = draft.g_number
        val paying_id = draft.id
        val groupNameQuery = ktormDatabase.from(DBUserTable).select(DBUserTable.id).where {
            DBUserTable.g_number eq g_number
        }
        val totalMembers = groupNameQuery.totalRecords
        val amountPerHead: Int = draft.amount / totalMembers
        for (row in groupNameQuery) {
            val d_id = row[DBUserTable.id]
            if (d_id == paying_id) continue
            ktormDatabase.insertOrUpdate(DBTransactionTable) {
                set(DBTransactionTable.id_src, d_id)
                set(DBTransactionTable.id_dest, paying_id)
                set(DBTransactionTable.amount, amountPerHead)
                onDuplicateKey {
                    set(DBTransactionTable.amount, DBTransactionTable.amount + amountPerHead)
                }
            }

        }

        return 0
    }
    //GroupInfo endpoints
    fun addGroup(draft: GroupInfoDraft): Int {
        ktormDatabase.insert(DBGroupInfoTable){
            set(DBGroupInfoTable.g_number,draft.g_number)
        }
        return 0
    }

}