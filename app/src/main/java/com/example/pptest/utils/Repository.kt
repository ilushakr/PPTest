package com.example.pptest.utils

import com.example.pptest.api.Api
import com.example.pptest.entities.TransactionHistoryDB
import com.example.pptest.entities.UserDB
import com.example.pptest.entities.ValuteDb
import com.example.pptest.room.TransactionsDao
import com.example.pptest.room.UsersDao
import com.example.pptest.room.ValuteDao
import javax.inject.Inject

class Repository @Inject constructor(
        private val api: Api,
        private val usersDatabase: UsersDao,
        private val transactionsDatabase: TransactionsDao,
        private val valuteDatabase: ValuteDao
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

    fun insertValutes(valutes: List<ValuteDb>) = valuteDatabase.insertValutes(valutes)

    fun getValutes() = valuteDatabase.getValutes()

    fun nukeTables(){
        usersDatabase.nukeTable()
        transactionsDatabase.nukeTable()
    }


}