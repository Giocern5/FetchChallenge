package com.example.fetchchallenge.di

import android.content.Context
import com.example.fetchchallenge.data.repository.UserRepository
import com.example.fetchchallenge.data.repository.UserRepositoryImpl
import com.example.fetchchallenge.network.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRetrofit() : UserService {
        val instance = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return instance.create(UserService::class.java)
    }

    @Provides
    fun provideWeatherRepository(service: UserService): UserRepository {
        return UserRepositoryImpl(service)
    }
}