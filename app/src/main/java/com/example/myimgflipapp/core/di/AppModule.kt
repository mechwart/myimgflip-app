package com.example.myimgflipapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.myimgflipapp.BuildConfig
import com.example.myimgflipapp.data.db.MemeDao
import com.example.myimgflipapp.data.db.MemeDatabase
import com.example.myimgflipapp.data.remote.MemeApiService
import com.example.myimgflipapp.data.repository.MemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideMemeRepository(
        memeDao: MemeDao,
        apiService: MemeApiService
    ): MemeRepository {
        return MemeRepository(memeDao, apiService)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MemeDatabase {
        return Room.databaseBuilder(
            context,
            MemeDatabase::class.java,
            "meme_database"
        ).build()
    }

    @Provides
    fun provideMemeDao(database: MemeDatabase): MemeDao {
        return database.memeDao()
    }
}