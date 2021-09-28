package com.example.selfcare.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.selfcare.constants.Constants.DATABASE_NAME
import com.example.selfcare.data.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Used to provide ROOM database builder
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ReminderDatabase::class.java,
        DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideDao(database: ReminderDatabase) = database.reminderDao()

}