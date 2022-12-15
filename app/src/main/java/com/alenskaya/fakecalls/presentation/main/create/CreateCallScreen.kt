package com.alenskaya.fakecalls.presentation.main.create

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.DialogsDisplayer
import com.alenskaya.fakecalls.presentation.Subscribe
import com.alenskaya.fakecalls.presentation.main.create.model.DateTimePickerData
import com.alenskaya.fakecalls.presentation.main.create.ui.CreateCallForm
import com.alenskaya.fakecalls.presentation.main.create.ui.DateTimePickerDialog
import com.alenskaya.fakecalls.presentation.showToast
import java.util.Date

/**
 * Screen for creating a new call
 */
@Composable
fun CreateCallScreen(
    mode: CreateCallScreenMode,
    viewModel: CreateCallScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.setMode(mode)
    }

    val createCallScreenState by viewModel.screenState.collectAsState()

    CreateCallScreen(
        createCallScreenState = createCallScreenState,
        imageLoader = viewModel.imageLoader,
        createStrings = viewModel.createStrings,
        navigateBackDescription = viewModel.createStrings.navigateBack(),
        navigateBackAction = {
            viewModel.sendEvent(CreateCallScreenEvent.ClickBack)
        },
        nameChanged = { newName ->
            viewModel.sendEvent(CreateCallScreenEvent.NameChanged(newName))
        },
        phoneChanged = { newPhone ->
            viewModel.sendEvent(CreateCallScreenEvent.PhoneChanged(newPhone))
        },
        calendarClicked = {
            viewModel.sendEvent(CreateCallScreenEvent.ShowDatePicker)
        },
        submitClicked = {
            viewModel.sendEvent(CreateCallScreenEvent.SubmitForm)
        }
    )

    val context = LocalContext.current

    viewModel.oneTimeEffect.Subscribe { oneTimeUiEffect ->
        processOneTimeUiEffect(
            createStrings = viewModel.createStrings,
            oneTimeUiEffect = oneTimeUiEffect,
            context = context,
            dialogsDisplayer = viewModel.dialogsDisplayer,
            updateDateCallBack = { newDate ->
                viewModel.sendEvent(CreateCallScreenEvent.DateChanged(newDate))
            }
        )
    }
}

@Composable
private fun CreateCallScreen(
    createCallScreenState: CreateCallScreenState,
    imageLoader: ImageLoader,
    createStrings: CreateStrings,
    navigateBackDescription: String,
    navigateBackAction: () -> Unit,
    nameChanged: (String) -> Unit,
    phoneChanged: (String) -> Unit,
    calendarClicked: () -> Unit,
    submitClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            NavigationBackIcon(navigateBackDescription) {
                navigateBackAction()
            }
            CreateCallForm(
                createCallScreenState = createCallScreenState,
                imageLoader = imageLoader,
                createStrings = createStrings,
                nameChanged = nameChanged,
                phoneChanged = phoneChanged,
                calendarClicked = calendarClicked,
                submitClicked = submitClicked
            )
        }
    }
}

private fun processOneTimeUiEffect(
    createStrings: CreateStrings,
    oneTimeUiEffect: CreateCallScreenOneTimeUiEffect,
    context: Context,
    dialogsDisplayer: DialogsDisplayer,
    updateDateCallBack: (Date) -> Unit
) {
    when (oneTimeUiEffect) {
        is CreateCallScreenOneTimeUiEffect.ShowDatePicker -> showDatePicker(
            datePickerTitle = createStrings.datePickerTitle(),
            timePickerTitle = createStrings.timePickerTitle(),
            dialogsDisplayer = dialogsDisplayer,
            dateTimePickerData = oneTimeUiEffect.dateTimePickerData,
            updateDateCallBack = updateDateCallBack
        )
        is CreateCallScreenOneTimeUiEffect.ShowToast -> {
            context.showToast(oneTimeUiEffect.message)
        }
    }
}

@Composable
private fun NavigationBackIcon(
    description: String,
    doOnBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            contentDescription = description,
            imageVector = ImageVector.vectorResource(id = R.drawable.back_icon),
            modifier = Modifier.clickable {
                doOnBackClick()
            }
        )
    }
}

private fun showDatePicker(
    datePickerTitle: String,
    timePickerTitle: String,
    dialogsDisplayer: DialogsDisplayer,
    dateTimePickerData: DateTimePickerData,
    updateDateCallBack: (Date) -> Unit
) {
    DateTimePickerDialog(datePickerTitle, timePickerTitle, dialogsDisplayer)
        .pickDate(dateTimePickerData, updateDateCallBack)
}
