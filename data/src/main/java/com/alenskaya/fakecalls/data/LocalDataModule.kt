package com.alenskaya.fakecalls.data

import android.content.Context
import androidx.room.Room
import com.alenskaya.fakecalls.data.local.db.CallsRoomRepository
import com.alenskaya.fakecalls.data.local.db.calls.CallDao
import com.alenskaya.fakecalls.data.local.db.FakeCallsDatabase
import com.alenskaya.fakecalls.data.local.db.ContactsRoomRepository
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactDao
import com.alenskaya.fakecalls.domain.calls.CallsRepository
import com.alenskaya.fakecalls.domain.contacts.ContactsLocalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DI for local data sources
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalDataModule {

    @Binds
    @Singleton
    abstract fun callsRepository(callsRoomRepository: CallsRoomRepository): CallsRepository

    @Binds
    @Singleton
    abstract fun fakeContactsLocalRepository(impl: ContactsRoomRepository): ContactsLocalRepository

    companion object {

        @Provides
        @Singleton
        fun provideCallsDao(database: FakeCallsDatabase): CallDao = database.callDao()

        @Provides
        @Singleton
        fun provideFakeContactsDaoDao(database: FakeCallsDatabase): FakeContactDao =
            database.fakeContactDao()

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): FakeCallsDatabase =
            Room.databaseBuilder(
                context,
                FakeCallsDatabase::class.java, "fake-calls-database"
            )
                .addMigrations(FakeCallsDatabase.MIGRATION_1_2)
                .addMigrations(FakeCallsDatabase.MIGRATION_2_3)
                .build()
    }
}
