package com.example.shopsphere.di

import android.app.Application
import androidx.room.Room
import com.example.shopsphere.data.local.LocalDataSource
import com.example.shopsphere.data.local.SearchDao
import com.example.shopsphere.data.local.SearchDatabase
import com.example.shopsphere.data.remote.RemoteDataSource
import com.example.shopsphere.data.remote.SearchApiService
import com.example.shopsphere.data.repository.SearchRepositoryImpl
import com.example.shopsphere.domain.repository.SearchRepository
import com.example.shopsphere.domain.usecase.GetCachedSuggestionsUseCase
import com.example.shopsphere.domain.usecase.SaveSuggestionsUseCase
import com.example.shopsphere.domain.usecase.SearchProductsUseCase
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

    // === NETWORK ===
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mockapi.io/api/") // 🔧 Replace with your real/mock endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    // === DATABASE ===
    @Provides
    @Singleton
    fun provideDatabase(app: Application): SearchDatabase {
        return Room.databaseBuilder(
            app,
            SearchDatabase::class.java,
            "search_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSearchDao(db: SearchDatabase) = db.searchDao()

    // === DATA SOURCES ===
    @Provides
    fun provideRemoteDataSource(api: SearchApiService) = RemoteDataSource(api)

    @Provides
    fun provideLocalDataSource(dao: SearchDao): LocalDataSource {
        return LocalDataSource(dao)
    }

    // === REPOSITORY ===
    @Provides
    @Singleton
    fun provideSearchRepository(
        remote: RemoteDataSource,
        local: LocalDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(remote, local)
    }

    // === USE CASES ===
    @Provides
    fun provideSearchProductsUseCase(repository: SearchRepository): SearchProductsUseCase {
        return SearchProductsUseCase(repository)
    }

    @Provides
    fun provideGetCachedSuggestionsUseCase(repository: SearchRepository): GetCachedSuggestionsUseCase {
        return GetCachedSuggestionsUseCase(repository)
    }

    @Provides
    fun provideSaveSuggestionsUseCase(repository: SearchRepository): SaveSuggestionsUseCase {
        return SaveSuggestionsUseCase(repository)
    }
}
