package com.alenskaya.fakecalls.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alenskaya.fakecalls.data.local.db.calls.CallDao
import com.alenskaya.fakecalls.data.local.db.calls.CallEntity
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactDao
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntity

/**
 * Room database for storing fake calls
 */
@Database(
    entities = [CallEntity::class, FakeContactEntity::class],
    version = 2
)
internal abstract class FakeCallsDatabase : RoomDatabase() {

    abstract fun callDao(): CallDao

    abstract fun fakeContactDao(): FakeContactDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE fake_contacts ADD COLUMN country TEXT NOT NULL DEFAULT 'Poland' ")
            }
        }
    }
}
