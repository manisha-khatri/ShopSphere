package com.example.shopsphere.di

import android.content.Context
import androidx.room.Room
import com.example.shopsphere.data.cache.SearchSuggestionsDao
import com.example.shopsphere.data.cache.SearchSuggestionsDatabase
import com.example.shopsphere.data.network.SearchApiService
import com.example.shopsphere.data.repository.SearchRepositoryImpl
import com.example.shopsphere.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSearchSuggestionsRepository(
        api: SearchApiService,
        dao: SearchSuggestionsDao
    ): SearchRepository {
        return SearchRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mockfly.dev/mocks/afc70459-bddc-4d16-b6b3-1b649eec78bc/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchSuggestionsApi(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SearchSuggestionsDatabase {
        return Room.databaseBuilder(
            context,
            SearchSuggestionsDatabase::class.java,
            "search_suggestions_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchSuggestionsDao(database: SearchSuggestionsDatabase): SearchSuggestionsDao {
        return database.searchSuggestionsDao()
    }
}