package com.alenskaya.fakecalls.presentation.features.create

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
import com.alenskaya.fakecalls.presentation.features.create.model.DateTimePickerData
import com.alenskaya.fakecalls.presentation.showToast
import kotlinx.coroutines.flow.SharedFlow
import java.util.Date

@Composable
fun CreateCallScreen(
    mode: CreateCallScreenMode,
    viewModel: CreateCallScreenViewModel = hiltViewModel()
) {
    val createCallScreenState by viewModel.screenState.collectAsState()

    CreateCallScreen(
        createCallScreenState = createCallScreenState,
        imageLoader = viewModel.imageLoader,
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
            NavigationBackIcon {
                navigateBackAction()
            }
            CreateCallForm(
                createCallScreenState = createCallScreenState,
                imageLoader = imageLoader,
                nameChanged = nameChanged,
                phoneChanged = phoneChanged,
                calendarClicked = calendarClicked,
                submitClicked = submitClicked
            )
        }
    }
}

@Composable
private fun SharedFlow<CreateCallScreenOneTimeUiEffect>.Subscribe(
    doWhenReceive: (CreateCallScreenOneTimeUiEffect) -> Unit
) {
    LaunchedEffect(true) {
        collect { oneTimeUiEffect ->
            doWhenReceive(oneTimeUiEffect)
        }
    }
}

private fun processOneTimeUiEffect(
    oneTimeUiEffect: CreateCallScreenOneTimeUiEffect,
    context: Context,
    dialogsDisplayer: DialogsDisplayer,
    updateDateCallBack: (Date) -> Unit
) {
    when (oneTimeUiEffect) {
        is CreateCallScreenOneTimeUiEffect.ShowDatePicker -> showDatePicker(
            dialogsDisplayer,
            oneTimeUiEffect.dateTimePickerData,
            updateDateCallBack
        )
        is CreateCallScreenOneTimeUiEffect.ShowToast -> {
            context.showToast(oneTimeUiEffect.message)
        }
    }
}

@Composable
private fun NavigationBackIcon(doOnBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            contentDescription = "Navigate back", //FIXME
            imageVector = ImageVector.vectorResource(id = R.drawable.back_icon),
            modifier = Modifier.clickable {
                doOnBackClick()
            }
        )
    }
}

private fun showDatePicker(
    dialogsDisplayer: DialogsDisplayer,
    dateTimePickerData: DateTimePickerData,
    updateDateCallBack: (Date) -> Unit
) {
    DateTimePickerDialog(dialogsDisplayer)
        .pickDate(dateTimePickerData, updateDateCallBack)
}
