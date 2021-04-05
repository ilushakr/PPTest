package com.example.pptest.cards

import androidx.lifecycle.ViewModel
import com.example.pptest.Repository
import javax.inject.Inject

class CardsFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getUsers() = repository.getUsersFromDb()
}