package com.example.pptest.mainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainFragmentViewModelFactory @Inject constructor(private val viewModel: MainFragmentViewModel) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}