package com.alenskaya.fakecalls.data.local.db.calls

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Describes how calls are stored in the calls table
 */
@Entity(tableName = "calls")
internal data class CallEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "contact_name")
    val contactName: String,

    @ColumnInfo(name = "contact_phone")
    val contactPhone: String,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String?,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "status")
    val status: String
)
