package com.rahul.networkclient.di

import android.app.Application
import androidx.room.Room
import com.rahul.networkclient.database.DogImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by abrol at 17/08/23.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, DogImageDatabase::class.java, "dog_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesUserDao(dogImageDatabase: DogImageDatabase) =
        dogImageDatabase.dogImageDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope