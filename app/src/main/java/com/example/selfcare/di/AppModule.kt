package com.example.selfcare.di

import android.content.Context
import com.example.selfcare.BaseApplication
import com.example.selfcare.data.model.repositories.ReminderRepository
import com.example.selfcare.data.model.repositories.ReminderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependencies (Objects) can be replacable
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    @Singleton
    fun provideReminderRepository(): ReminderRepository {
        return ReminderRepositoryImpl()
    }

}