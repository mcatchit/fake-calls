package com.alenskaya.fakecalls.presentation.features.create

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
import com.alenskaya.fakecalls.presentation.showToast
import java.util.Date

@Composable
fun CreateCallScreen(
    mode: CreateCallScreenMode,
    viewModel: CreateCallScreenViewModel = hiltViewModel()
) {
    val createCallScreenState by viewModel.screenState.collectAsState()
    val oneTimeUiEvent by viewModel.oneTimeEffect.collectAsState(initial = null)

    val navigateBackAction = {
        viewModel.sendEvent(CreateCallScreenEvent.ClickBack)
    }
    val nameChangedAction = { newName: String ->
        viewModel.sendEvent(CreateCallScreenEvent.NameChanged(newName))
    }

    val phoneChangedAction = { newPhone: String ->
        viewModel.sendEvent(CreateCallScreenEvent.PhoneChanged(newPhone))
    }

    val calendarClickedAction = {
        viewModel.sendEvent(CreateCallScreenEvent.ShowDatePicker)
    }

    val submitFormAction = {
        viewModel.sendEvent(CreateCallScreenEvent.SubmitForm)
    }

    val updateDateCallBack = { date: Date ->
        viewModel.sendEvent(CreateCallScreenEvent.DateChanged(date))
    }

    CreateCallScreen(
        createCallScreenState = createCallScreenState,
        imageLoader = viewModel.imageLoader,
        navigateBackAction = navigateBackAction,
        nameChanged = nameChangedAction,
        phoneChanged = phoneChangedAction,
        calendarClicked = calendarClickedAction,
        submitClicked = submitFormAction
    )

    oneTimeUiEvent?.let {
        ProcessOneTimeUiEffect(oneTimeUiEffect = it, updateDateCallBack = updateDateCallBack)
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
private fun ProcessOneTimeUiEffect(
    oneTimeUiEffect: CreateCallScreenOneTimeUiEffect,
    updateDateCallBack: (Date) -> Unit
) {
    when (oneTimeUiEffect) {
        is CreateCallScreenOneTimeUiEffect.ShowDatePicker -> ShowDatePicker(
            oneTimeUiEffect.datePickerData,
            updateDateCallBack
        )
        is CreateCallScreenOneTimeUiEffect.ShowToast -> {
            LocalContext.current.showToast(oneTimeUiEffect.message)
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

@Composable
private fun ShowDatePicker(datePickerData: DatePickerData, updateDateCallBack: (Date) -> Unit) {

}
