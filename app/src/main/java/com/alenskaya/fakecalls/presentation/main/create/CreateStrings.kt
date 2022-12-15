package com.alenskaya.fakecalls.presentation.main.create

import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.AppStrings
import javax.inject.Inject

/**
 * String resources for create screen.
 */
interface CreateStrings {
    fun navigateBack(): String
    fun alarmPermissionToast(): String
    fun notificationPermissionToast(): String
    fun tryAgainToast(): String
    fun fillAllFieldsToast(): String
    fun invalidDateToast(): String
    fun datePickerTitle(): String
    fun timePickerTitle(): String
    fun nameLabel(): String
    fun phoneLabel(): String
    fun dateLabel(): String
    fun dateDescription(): String
    fun photoDescription(): String
    fun createNewTitle(): String
    fun editCallTitle(): String
    fun repeatCallTitle(): String
    fun createButton(): String
    fun saveButton(): String
}

class CreateStringsImpl @Inject constructor(
    private val appStrings: AppStrings
) : CreateStrings {

    override fun navigateBack(): String {
        return appStrings.getString(R.string.navigate_back)
    }

    override fun alarmPermissionToast(): String {
        return appStrings.getString(R.string.create_screen_alarm_permission_toast)
    }

    override fun notificationPermissionToast(): String {
        return appStrings.getString(R.string.create_screen_notification_permission_toast)
    }

    override fun tryAgainToast(): String {
        return appStrings.getString(R.string.create_screen_try_again_toast)
    }

    override fun fillAllFieldsToast(): String {
        return appStrings.getString(R.string.create_screen_fill_all_fields_toast)
    }

    override fun invalidDateToast(): String {
        return appStrings.getString(R.string.create_screen_invalid_date_toast)
    }

    override fun datePickerTitle(): String {
        return appStrings.getString(R.string.create_screen_date_picker_title)
    }

    override fun timePickerTitle(): String {
        return appStrings.getString(R.string.create_screen_time_picker_title)
    }

    override fun nameLabel(): String {
        return appStrings.getString(R.string.create_screen_name_label)
    }

    override fun phoneLabel(): String {
        return appStrings.getString(R.string.create_screen_phone_label)
    }

    override fun dateLabel(): String {
        return appStrings.getString(R.string.create_screen_date_label)
    }

    override fun dateDescription(): String {
        return appStrings.getString(R.string.create_screen_date_description)
    }

    override fun photoDescription(): String {
        return appStrings.getString(R.string.create_screen_date_description)
    }

    override fun createNewTitle(): String {
        return appStrings.getString(R.string.create_screen_create_new_title)
    }

    override fun editCallTitle(): String {
        return appStrings.getString(R.string.create_screen_edit_title)
    }

    override fun repeatCallTitle(): String {
        return appStrings.getString(R.string.create_screen_repeat_title)
    }

    override fun createButton(): String {
        return appStrings.getString(R.string.create_screen_create_button)
    }

    override fun saveButton(): String {
        return appStrings.getString(R.string.create_screen_save_button)
    }
}
