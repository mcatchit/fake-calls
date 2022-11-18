package com.alenskaya.fakecalls.data.local.db.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Describes how fake contacts are stored in the fake contacts table
 */
@Entity(tableName = "fake_contacts")
internal data class FakeContactEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String?
)
