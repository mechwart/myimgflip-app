package com.example.myimgflipapp.core.di

import com.example.myimgflipapp.BuildConfig
import com.example.myimgflipapp.data.remote.MemeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMemeApiService(retrofit: Retrofit): MemeApiService {
        return retrofit.create(MemeApiService::class.java)
    }
}