package com.example.pptest.modules

import com.example.pptest.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityComponent::class)
object ApiModule {

    @Provides
    fun provideApi(): Api {
        return Retrofit.Builder()
            .baseUrl("https://hr.peterpartner.net/test/android/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .callTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(2, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .build().create(Api::class.java)
    }
}