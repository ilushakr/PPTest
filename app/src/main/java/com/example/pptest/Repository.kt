package com.example.pptest

import com.example.pptest.api.Api
import com.example.pptest.entities.TransactionHistoryDB
import com.example.pptest.entities.UserDB
import com.example.pptest.room.TransactionsDao
import com.example.pptest.room.UsersDao
import javax.inject.Inject

class Repository @Inject constructor(
        private val api: Api,
        private val usersDatabase: UsersDao,
        private val transactionsDatabase: TransactionsDao
    ) {

    fun getUsersApi() = api.getUsers()

    fun insertUsers(users: List<UserDB>, transactions: List<TransactionHistoryDB>){
        usersDatabase.insertUser(users)
        if(transactions.isNotEmpty()) {
            transactionsDatabase.insertTransactions(transactions)
        }
    }

    fun getCurrency() = api.getCurrency(url = "https://www.cbr-xml-daily.ru/daily_json.js")

    fun getUserFromDb(usersCardNumber: String) = usersDatabase.getUser(usersCardNumber)

    fun getUserFromDb(id: Int) = usersDatabase.getUser(id)

    fun getUsersHistory(userId: Int) = transactionsDatabase.getTransactions(userId)

    fun getUsersFromDb() = usersDatabase.getUsers()

    fun nukeTables(){
        usersDatabase.nukeTable()
        transactionsDatabase.nukeTable()
    }


}