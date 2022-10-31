package com.alenskaya.fakecalls.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database for storing fake calls
 */
@Database(
    entities = [CallEntity::class],
    version = 1
)
internal abstract class FakeCallsDatabase : RoomDatabase() {

    abstract fun callDao(): CallDao
}
