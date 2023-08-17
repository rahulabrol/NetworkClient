package com.rahul.networkclient.di

import com.rahul.networkclient.DogImageManager
import com.rahul.networkclient.DogImageManagerImpl
import com.rahul.networkclient.constant.ApiUrls
import com.rahul.networkclient.database.DogImageDao
import com.rahul.networkclient.service.DogService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by abrol at 15/08/23.
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {

    private val responseTimeOut: Int
        get() = 120

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.connectTimeout(responseTimeOut.toLong(), TimeUnit.SECONDS)
        okHttpClient.readTimeout(responseTimeOut.toLong(), TimeUnit.SECONDS)
        okHttpClient.writeTimeout(responseTimeOut.toLong(), TimeUnit.SECONDS)
        okHttpClient.hostnameVerifier { _, _ -> true }
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
        chain.proceed(request.build())
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiUrls.baseUrl.orEmpty())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): DogService = retrofit.create(DogService::class.java)

    @Singleton
    @Provides
    fun providesRepo(api: DogService, dogImageDao: DogImageDao): DogImageManager {
        return DogImageManagerImpl(api, dogImageDao)
    }
}