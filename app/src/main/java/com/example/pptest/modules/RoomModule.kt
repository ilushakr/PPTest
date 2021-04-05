package com.example.pptest.modules

import android.content.Context
import androidx.room.Room
import com.example.pptest.room.TransactionsDao
import com.example.pptest.room.TransactionsDatabase
import com.example.pptest.room.UsersDao
import com.example.pptest.room.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object RoomModule {

    @Provides
    fun provideUsersDataBase(@ApplicationContext context: Context):UsersDatabase{
        return Room.databaseBuilder(
            context,
            UsersDatabase::class.java,
            UsersDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUsersDao(usersDatabase: UsersDatabase): UsersDao {
        return usersDatabase.usersDao()
    }

    @Provides
    fun provideTransactionsDataBase(@ApplicationContext context: Context):TransactionsDatabase{
        return Room.databaseBuilder(
            context,
            TransactionsDatabase::class.java,
            TransactionsDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTransactionsDao(transactionsDatabase: TransactionsDatabase): TransactionsDao {
        return transactionsDatabase.transactionsDao()
    }

}