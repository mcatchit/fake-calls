package com.alenskaya.fakecalls.presentation.phonebook

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract

/**
 * Retrieves contacts from phonebook.
 * Before usage Manifest.permission.READ_CONTACTS should be granted.
 * @property contentResolver - application content resolver.
 */
class PhonebookContactsRetriever(
    private val contentResolver: ContentResolver
) {

    /**
     * Returns list of all contacts stored in user's phonebook.
     */
    fun getAllContacts(): List<PhonebookContact> {
        val contactsList = mutableListOf<PhonebookContact>()

        getContactsCursor().use { cursor ->

            cursor?.let {
                if (cursor.count > 0) {

                    while (cursor.moveToNext()) {

                        cursor.extractContact()?.let { contact ->
                            contactsList.add(contact)
                        }
                    }
                }
            }
        }

        return contactsList.sortedBy { it.name }
    }

    private fun getContactsCursor(): Cursor? = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        ),
        null,
        null,
        null
    )

    @SuppressLint("Range")
    private fun Cursor.extractContact(): PhonebookContact? {
        return try {
            PhonebookContact(
                id = getColumnValue(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID),
                name = getColumnValue(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),
                phone = getColumnValue(ContactsContract.CommonDataKinds.Phone.NUMBER),
                photo = getPhotoUri()
            )
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("Range")
    private fun Cursor.getColumnValue(columnName: String) = getString(getColumnIndex(columnName))

    @SuppressLint("Range")
    private fun Cursor.getPhotoUri(): String? = try {
        getColumnValue(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
    } catch (e: Exception) {
        null
    }
}
