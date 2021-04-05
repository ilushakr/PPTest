package com.example.pptest.mainFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pptest.utils.Repository
import com.example.pptest.entities.*
import com.example.pptest.utils.Utils.addChar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _savingState = MutableStateFlow<String?>(null)
    val savingState: StateFlow<String?> = _savingState

    val _transactions = MutableLiveData<List<TransactionHistory>?>(null)

    var transactions = listOf<TransactionHistory>()

    val currencyBalance = MutableLiveData("")
    var user = MutableLiveData<UserDB>()

    val currencies = MutableLiveData<List<Valute>>()

    var isInternetConnection = true

    private var usd = 0.0
    private var gbp = 0.0
    private var eur = 0.0

    private fun getUsersApi() {
        repository.getUsersApi().enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                response.body().let {
                    val usersApi = it!!.users
                    val usersDB = userForDB(usersApi)
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.insertUsers(usersDB.first, usersDB.second)
                        _savingState.value = usersApi[0].cardNumber
                    }
                }

            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                print(4)
            }

        })
    }

    fun changeBalance(v: String){
        when(v){
            "RUB" -> {
                currencyBalance.value = "₽${String.format("%.2f", (user.value!!.balance * usd))}"
                changeHistoryBalance(" ₽ ", usd)
            }
            "EUR" -> {
                currencyBalance.value =
                    "€${String.format("%.2f", (user.value!!.balance * usd / eur))}"
                changeHistoryBalance(" € ", usd / eur)
            }
            "GBP" -> {
                currencyBalance.value =
                    "£${String.format("%.2f", (user.value!!.balance * usd / gbp))}"

                changeHistoryBalance(" £ ", usd / gbp)
            }
        }
    }

    fun changeHistoryBalance(ch: String, coef: Double){
        if(transactions.isNotEmpty()){
            transactions.forEach {
                it.balanceSigned = addChar(
                    String.format("%.2f", (it.balanceUsd.toDouble() * coef)),
                    ch
                )
            }
            _transactions.value = transactions
        }
    }

    fun getCurrency(): MutableLiveData<List<Valute>>{
        repository.getCurrency().enqueue(object : Callback<Currencies> {
            override fun onResponse(call: Call<Currencies>, response: Response<Currencies>) {
                if (response.body() != null){
                    val currenciesMap = response.body()!!.valute.filter {
                        it.key == "GBP" || it.key == "USD" || it.key == "EUR"
                    }
                    currencies.value = currenciesMap.map {
                        Valute(value = it.value.value, name = it.key)
                    }
                    currencies.value!!.forEach {
                        if(it.name == "USD") usd = it.value
                        if(it.name == "EUR") eur = it.value
                        if(it.name == "GBP") gbp = it.value
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        repository.insertValutes(currencies.value!!.map {
                            ValuteDb(
                                name = it.name,
                                value = it.value
                            )
                        })
                    }

                    changeHistoryBalance("£", usd / gbp)

                    currencyBalance.value = "£${String.format("%.2f", (user.value!!.balance * usd / gbp))}"
                }

            }

            override fun onFailure(call: Call<Currencies>, t: Throwable) {

            }

        })

        return currencies
    }

    fun getUserDb(usersCardNumber: String) = repository.getUserFromDb(usersCardNumber)

    fun getUserDb(id: Int) = repository.getUserFromDb(id)

    fun getUsersHistory(userId: Int) = repository.getUsersHistory(userId)

    fun clearData(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.nukeTables()
            getUsersApi()
        }
    }

    private fun userForDB(users: List<User>) : Pair<List<UserDB>, List<TransactionHistoryDB>> {
        val usersList = mutableListOf<UserDB>()
        val transactionsList = mutableListOf<TransactionHistoryDB>()

        users.forEachIndexed { index, user ->
            usersList.add(
                UserDB(
                    usersId = index,
                    cardholderName = user.cardholderName,
                    type = user.type,
                    cardNumber = user.cardNumber,
                    valid = user.valid,
                    balance = user.balance
                )
            )
            if(user.transactionHistoryApi.isNotEmpty()){
                user.transactionHistoryApi.forEach { transaction ->
                    transactionsList.add(
                        TransactionHistoryDB(
                            usersId = index,
                            title = transaction.title,
                            iconUrl = transaction.iconUrl,
                            date = transaction.date,
                            amount = transaction.amount
                        )
                    )
                }
            }
        }

        return Pair(usersList, transactionsList)
    }

    fun getValutes() = repository.getValutes()

    fun setCurrencies(valutes: List<ValuteDb>){
        currencies.value = valutes.map {
            Valute(
                name = it.name,
                value = it.value
            )
        }

        currencies.value!!.forEach {
            if(it.name == "USD") usd = it.value
            if(it.name == "EUR") eur = it.value
            if(it.name == "GBP") gbp = it.value
        }

        changeHistoryBalance("£", usd / gbp)

        currencyBalance.value = "£${String.format("%.2f", (user.value!!.balance * usd / gbp))}"
    }
}