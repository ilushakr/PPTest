package com.example.pptest.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pptest.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cards_fragment_layout.view.*
import javax.inject.Inject

@AndroidEntryPoint
class CardsFragment: Fragment(R.layout.cards_fragment_layout), CardsAdapter.AdapterListener {

    @Inject
    lateinit var factory : CardsFragmentViewModelFactory
    private lateinit var viewModel: CardsFragmentViewModel
    private var cardNumber: String? = null
    private var selectedCard = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(CardsFragmentViewModel::class.java)
        print(4)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.toolBar.setNavigationIcon(R.drawable.ic_vector_up_button)
        view.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        cardNumber = arguments?.getString("cardNumber")
        selectedCard = cardNumber!!
        viewModel.getUsers().observe(viewLifecycleOwner, {users ->
            view.cardsRecyclerView.adapter = CardsAdapter(users).also { cardsAdapter ->
                cardsAdapter.setListener(this)
                cardsAdapter.setCurrentCard(currentCard = cardNumber!!)
            }
        })
    }

    override fun setCard(card: String) {
        selectedCard = card
        findNavController().previousBackStackEntry?.savedStateHandle?.set("SELECTED_CARD", selectedCard)
        findNavController().popBackStack()
    }


}