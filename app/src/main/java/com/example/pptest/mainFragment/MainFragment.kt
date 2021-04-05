package com.example.pptest.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.pptest.R
import com.example.pptest.databinding.MainFragmentLayoutBinding
import com.example.pptest.entities.TransactionHistory
import com.example.pptest.entities.TransactionHistoryDB
import com.example.pptest.entities.UserDB
import com.example.pptest.entities.Valute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cards_layout.view.*
import kotlinx.android.synthetic.main.currency_layout.view.*
import kotlinx.android.synthetic.main.history_layout.view.*
import kotlinx.android.synthetic.main.main_fragment_layout.view.*
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment: Fragment(R.layout.main_fragment_layout), View.OnClickListener {

    private var _binding: MainFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    var cardNumber: String? = null
    private lateinit var user: UserDB
    private val historyAdapter = HistoryAdapter()

    @Inject
    lateinit var factory : MainFragmentViewModelFactory
    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = MainFragmentLayoutBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardNumber = arguments?.getString("cardNumber")
        this.arguments?.clear()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("SELECTED_CARD")?.observe(
            viewLifecycleOwner) { result ->

            viewModel.getUserDb(result!!).observe(viewLifecycleOwner, { user ->
                if(user != null){
                    this.user = user
                    viewModel.user.value = user
                    getUsersTransactions(view, user)
                    viewModel.getValutes().observe(viewLifecycleOwner, { valutes ->
                        viewModel.setCurrencies(valutes)
                    })
                }
            })

        }

        viewModel.currencies.observe(viewLifecycleOwner, {
            if(it != null){
                view.currencyCardG.isClickable = true
                view.currencyCardE.isClickable = true
                view.currencyCardR.isClickable = true
            }
        })

        if(cardNumber != null && cardNumber != ""){
            viewModel.getUserDb(cardNumber!!).observe(viewLifecycleOwner, { user ->
                if(user != null){
                    cardNumber = ""
                    this.user = user
                    viewModel.user.value = user
                    getUsersTransactions(view, user)
                    viewModel.getCurrency().observe(viewLifecycleOwner, { currencies ->
                    })
                }
            })
        }
        else if(cardNumber == ""){
            viewModel.getUserDb(1).observe(viewLifecycleOwner, { user ->
                if(user != null){
                    this.user = user
                    viewModel.user.value = user
                    getUsersTransactions(view, user)
                    viewModel.getValutes().observe(viewLifecycleOwner, { valutes ->
                        viewModel.setCurrencies(valutes)
                    })
                }
            })
        }

        setUpCurrenciesButtons(view)

        viewModel._transactions.observe(viewLifecycleOwner, {updatedTransactions ->
            if(updatedTransactions != null && updatedTransactions.isNotEmpty()) {
                historyAdapter.setData(updatedTransactions)
            }
        })

    }

    private fun setUpCurrenciesButtons(view: View){
        view.currencyCardG.setOnClickListener{
            viewModel.changeBalance("GBP")
            changeColor(
                view.currencyIconG,
                view.currencyNameG,
                view.currencyCardG,
                view.currencyIconE,
                view.currencyNameE,
                view.currencyCardE,
                view.currencyIconR,
                view.currencyNameR,
                view.currencyCardR,
            )
        }
        view.currencyCardE.setOnClickListener{
            viewModel.changeBalance("EUR")
            changeColor(
                view.currencyIconE,
                view.currencyNameE,
                view.currencyCardE,
                view.currencyIconG,
                view.currencyNameG,
                view.currencyCardG,
                view.currencyIconR,
                view.currencyNameR,
                view.currencyCardR,
            )
        }
        view.currencyCardR.setOnClickListener{
            viewModel.changeBalance("RUB")
            changeColor(
                view.currencyIconR,
                view.currencyNameR,
                view.currencyCardR,
                view.currencyIconG,
                view.currencyNameG,
                view.currencyCardG,
                view.currencyIconE,
                view.currencyNameE,
                view.currencyCardE,
            )
        }
        view.cardsLayout.setOnClickListener(this)
    }

    private fun changeColor(
        selectedIcon: TextView,
        selectedText: TextView,
        selectedCard: CardView,
        firstIcon: TextView,
        firstText: TextView,
        firstCard: CardView,
        secondIcon: TextView,
        secondText: TextView,
        secondCard: CardView,
    ){
        selectedIcon.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        selectedText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        selectedCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))

        firstIcon.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        firstText.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        firstCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

        secondIcon.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        secondText.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        secondCard.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun getUsersTransactions(view: View, user: UserDB){
        viewModel.getUsersHistory(user.usersId).observe(viewLifecycleOwner, { transactions ->
            setUsersTransactions(view, transactions)
        })

        setCardsInformationView(view, user)
    }

    private fun setCardsInformationView(view: View, user: UserDB){
        view.cardNumber.text = user.cardNumber
        view.usersName.text = user.cardholderName
        view.cardsExpitationDate.text = user.valid
        view.cardsBalanceMain.text = context?.getString(R.string.usd_sign, user.balance.toString())

    }

    private fun  setUsersTransactions(view: View, transactions: List<TransactionHistoryDB>){
        val tr = transactions.map {
            TransactionHistory(
                title = it.title,
                iconUrl = it.iconUrl,
                date = it.date,
                balanceUsd = it.amount,
                balance = ""
            )
        }
        viewModel.transactions = tr

        historyAdapter.setData(tr)

        view.historyRecyclerView.adapter = historyAdapter
    }

    override fun onClick(v: View) {
        if(v.id ==  R.id.cardsLayout) {
            user.let {
                findNavController().navigate(R.id.toChangeCard, Bundle().also {
                    it.putString("cardNumber", user.cardNumber)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}