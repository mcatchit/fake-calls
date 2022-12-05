package com.alenskaya.fakecalls.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alenskaya.fakecalls.data.local.db.calls.CallDao
import com.alenskaya.fakecalls.data.local.db.calls.CallEntity
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactDao
import com.alenskaya.fakecalls.data.local.db.contacts.FakeContactEntity

/**
 * Room database for storing fake calls
 */
@Database(
    entities = [CallEntity::class, FakeContactEntity::class],
    version = 1
)
internal abstract class FakeCallsDatabase : RoomDatabase() {

    abstract fun callDao(): CallDao

    abstract fun fakeContactDao(): FakeContactDao
}
