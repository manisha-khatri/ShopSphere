package com.example.shopsphere.di

import com.example.shopsphere.data.local.LocalProductDataSource
import com.example.shopsphere.data.remote.RemoteProductDataSource
import android.app.Application
import androidx.room.Room
import com.example.shopsphere.data.local.LocalProductDataSourceImpl
import com.example.shopsphere.data.local.ProductDao
import com.example.shopsphere.data.local.ProductDatabase
import com.example.shopsphere.data.remote.ProductApiService
import com.example.shopsphere.data.remote.RemoteProductDataSourceImpl
import com.example.shopsphere.data.repository.ProductRepositoryImpl
import com.example.shopsphere.domain.repository.ProductRepository
import com.example.shopsphere.domain.usecase.GetAllProductsUseCase
import com.example.shopsphere.domain.usecase.GetSaleProductsUseCase
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

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ProductDatabase =
        Room.databaseBuilder(app, ProductDatabase::class.java, "product_db").build()

    @Provides
    fun provideDao(db: ProductDatabase): ProductDao = db.productDao()

    @Provides
    @Singleton
    fun provideApi(): ProductApiService = Retrofit.Builder()
        .baseUrl("https://api.yourstore.com/") // Replace with real API
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteProductDataSource(
        apiService: ProductApiService
    ): RemoteProductDataSource = RemoteProductDataSourceImpl(apiService)


    @Provides
    @Singleton
    fun provideProductRepository(
        remote: RemoteProductDataSource,
        local: LocalProductDataSource
    ): ProductRepository = ProductRepositoryImpl(remote, local)

    @Provides
    @Singleton
    fun provideLocalProductDataSource(
        dao: ProductDao
    ): LocalProductDataSource = LocalProductDataSourceImpl(dao)


    // UseCases
    @Provides fun provideGetAllProductsUseCase(repo: ProductRepository) = GetAllProductsUseCase(repo)
    @Provides fun provideSearchProductsUseCase(repo: ProductRepository) = SearchProductsUseCase(repo)
    @Provides fun provideGetSaleProductsUseCase(repo: ProductRepository) = GetSaleProductsUseCase(repo)
}
