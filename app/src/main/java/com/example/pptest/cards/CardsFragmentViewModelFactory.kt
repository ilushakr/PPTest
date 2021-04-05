package com.example.pptest.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pptest.mainFragment.MainFragmentViewModel
import javax.inject.Inject

class CardsFragmentViewModelFactory @Inject constructor(private val viewModel: CardsFragmentViewModel) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}