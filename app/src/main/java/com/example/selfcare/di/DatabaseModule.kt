package com.example.selfcare.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE reminder_table ADD COLUMN text TEXT NOT NULL DEFAULT ''")
        }
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ReminderDatabase::class.java,
        DATABASE_NAME
    )
        .addMigrations(MIGRATION_1_2)
        .build()


    @Singleton
    @Provides
    fun provideDao(database: ReminderDatabase) = database.reminderDao()

}