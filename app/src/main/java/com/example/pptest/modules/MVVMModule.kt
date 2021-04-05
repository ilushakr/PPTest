package com.example.pptest.modules

import com.example.pptest.utils.Repository
import com.example.pptest.api.Api
import com.example.pptest.cards.CardsFragmentViewModel
import com.example.pptest.cards.CardsFragmentViewModelFactory
import com.example.pptest.mainFragment.MainFragmentViewModel
import com.example.pptest.mainFragment.MainFragmentViewModelFactory
import com.example.pptest.room.TransactionsDao
import com.example.pptest.room.UsersDao
import com.example.pptest.room.ValuteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MVVMModule {

    @Provides
    fun provideRepository(
        api: Api,
        usersDatabase: UsersDao,
        transactionsDatabase: TransactionsDao,
        valuteDatabase: ValuteDao
    ): Repository {
        return Repository(api, usersDatabase, transactionsDatabase, valuteDatabase)
    }

    @Provides
    fun provideMainFragmentViewModel(repository: Repository): MainFragmentViewModel {
        return MainFragmentViewModel(repository)
    }

    @Provides
    fun provideMainFragmentViewModelFactory(mainFragmentViewModel: MainFragmentViewModel): MainFragmentViewModelFactory {
        return MainFragmentViewModelFactory(mainFragmentViewModel)
    }


    @Provides
    fun provideCardsFragmentViewModel(repository: Repository): CardsFragmentViewModel {
        return CardsFragmentViewModel(repository)
    }

    @Provides
    fun provideCardsFragmentViewModelFactory(cardsFragmentViewModel: CardsFragmentViewModel): CardsFragmentViewModelFactory {
        return CardsFragmentViewModelFactory(cardsFragmentViewModel)
    }
}