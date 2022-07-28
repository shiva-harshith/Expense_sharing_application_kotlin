package com.example.plugins

import com.example.database.DBUserTable
import com.example.entities.GroupInfoDraft
import com.example.entities.SplitTransaction
import com.example.entities.TransactionDraft
import com.example.entities.UserDraft
import com.example.repository.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where

fun Application.configureRouting() {

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    val ktormDatabase= Database.connect(
        url = "jdbc:mysql://localhost:3306/Expense?autoReconnect=true&useSSL=false",
        driver="com.mysql.cj.jdbc.Driver",
        user="root",
        password="Shiva_Harshith01"
    )
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        val repository: UserRepository = MySqlUserRepository()
        post("/User") {
            val userDraft = call.receive<UserDraft>()
            val user = repository.addUser(userDraft)
            call.respond(user)
        }
        get("/Users") {
            call.respond(repository.getAllUsers())
        }
        get("/Users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "id parameter has to be a number"
                )
                return@get
            }

            val user = repository.getUser(id)

            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "found no user for the provided id $id"
                )
            } else {
                call.respond(user)
            }
        }


        val repository2: TransactionRepository = MySqlTransactionRepository()

        get("/Transactions") {
            call.respond(repository2.getAllTransactions())
        }
        get("/Transactions/{id1}/{id2}") {
            val id1 = call.parameters["id1"]?.toIntOrNull()
            val id2 = call.parameters["id2"]?.toIntOrNull()

            if (id1 == null || id2==null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "id parameter has to be a number"
                )
                return@get
            }

            val transaction = repository2.getTransaction(id1,id2)

            if (transaction == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "found no transaction for the provided ids"
                )
            } else {
                call.respond(transaction)
            }

        }
        put("/Transactions/{id1}/{id2}") {
            val transactionDraft = call.receive<TransactionDraft>()
            val id1 = call.parameters["id1"]?.toIntOrNull()
            val id2 = call.parameters["id2"]?.toIntOrNull()

            if (id1 == null || id2==null) {
                call.respond(HttpStatusCode.BadRequest,
                    "id parameter has to be a number!")
                return@put
            }

            val updated = repository2.updateTransaction(id1,id2, transactionDraft)
            if (updated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound,
                    "found no transactions with the ids ")
            }
        }
        post("/Transaction") {
            val transactionDraft = call.receive<TransactionDraft>()
            val transaction = repository2.addTransaction(transactionDraft)
            call.respond(transaction)
        }


        val repository3: SplitTransactionRepository = MySqlSplitTransactionRepository()
        //groups
        post("/splitTransaction"){

            val splittransaction=call.receive<SplitTransaction>()
            val rnVal = repository3.addSplitTransaction(splittransaction)
            if(rnVal == -1){
                call.respond(HttpStatusCode.BadRequest , "No such group exists")
                return@post
            }

            call.respond(HttpStatusCode.OK)

        }


        val repository4: GroupInfoRepository = MySqlGroupInfoRepository()



        post("/group"){
            val groupInfoDraft=call.receive<GroupInfoDraft>()
            val groupInfo=repository4.addGroup(groupInfoDraft)
            call.respond(groupInfo)
        }

    }
}
