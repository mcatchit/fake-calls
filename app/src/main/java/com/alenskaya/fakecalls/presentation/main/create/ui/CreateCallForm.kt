package com.alenskaya.fakecalls.presentation.main.create.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.components.FakeContactIcon
import com.alenskaya.fakecalls.presentation.components.LoadingDots
import com.alenskaya.fakecalls.presentation.components.MainTitle
import com.alenskaya.fakecalls.presentation.main.create.CreateCallScreenState
import com.alenskaya.fakecalls.presentation.gesturesDisabled
import com.alenskaya.fakecalls.presentation.main.create.CreateStrings

/**
 * Displays main content of Create call screen.
 * @param createCallScreenState - state of the screen.
 * @param imageLoader - application image loader.
 * @param nameChanged - notifies that name input was changed.
 * @param phoneChanged - notifies that phone input was changed.
 * @param calendarClicked - notifies that calendar button was clicked.
 * @param submitClicked - notifies that submit button was clicked.
 * @param modifier - modifier.
 */
@Composable
fun CreateCallForm(
    createCallScreenState: CreateCallScreenState,
    imageLoader: ImageLoader,
    createStrings: CreateStrings,
    nameChanged: (String) -> Unit,
    phoneChanged: (String) -> Unit,
    calendarClicked: () -> Unit,
    submitClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .gesturesDisabled(createCallScreenState.isInitialDataLoading || createCallScreenState.isSubmitProcessing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(createCallScreenState) {
            MainTitle(text = formLabels.title)
            Spacer(modifier = Modifier.height(24.dp))
            Photo(
                url = formInput.photo,
                description = createStrings.photoDescription(),
                imageLoader = imageLoader
            )
            Spacer(modifier = Modifier.height(24.dp))
            InputForm(
                nameLabel = createStrings.nameLabel(),
                name = formInput.name,
                phoneLabel = createStrings.phoneLabel(),
                phone = formInput.phone,
                dateLabel = createStrings.dateLabel(),
                dateDescription = createStrings.dateDescription(),
                date = formInput.displayableDate,
                nameChanged = nameChanged,
                phoneChanged = phoneChanged,
                calendarClicked = calendarClicked
            )
            Spacer(modifier = Modifier.height(36.dp))
            SubmitButton(
                text = formLabels.buttonText,
                doOnClick = submitClicked,
                isGreen = createCallScreenState.isCreateButtonGreen,
                isLoading = createCallScreenState.isSubmitProcessing
            )
        }
    }
}

@Composable
private fun Photo(
    url: String?,
    description: String,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    FakeContactIcon(
        uri = url,
        size = 48.dp,
        description = description,
        imageLoader = imageLoader,
        modifier = modifier
    )
}

@Composable
private fun InputForm(
    nameLabel: String,
    name: String?,
    phoneLabel: String,
    phone: String?,
    dateLabel: String,
    dateDescription: String,
    date: String?,
    nameChanged: (String) -> Unit,
    phoneChanged: (String) -> Unit,
    calendarClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(corner = CornerSize(10.dp))
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                InputField(label = nameLabel, value = name ?: "", onValueChanged = nameChanged)
                Spacer(modifier = Modifier.height(12.dp))
                InputField(label = phoneLabel, value = phone ?: "", onValueChanged = phoneChanged)
                Spacer(modifier = Modifier.height(12.dp))
                DateField(
                    label = dateLabel,
                    description = dateDescription,
                    date = date ?: "",
                    onClick = calendarClicked
                )
            }
        }
    }
}

@Composable
private fun InputField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DateField(
    label: String,
    description: String,
    date: String,
    onClick: () -> Unit
) {
    OutlinedTextField(
        value = date,
        readOnly = true,
        onValueChange = {},
        label = { Text(label) },
        trailingIcon = {
            Icon(
                contentDescription = description,
                imageVector = ImageVector.vectorResource(id = R.drawable.calendar_icon),
                modifier = Modifier.clickable {
                    onClick()
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun SubmitButton(
    text: String,
    doOnClick: () -> Unit,
    isGreen: Boolean,
    isLoading: Boolean
) {
    val buttonColor =
        if (isGreen) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.onSecondary

    Button(
        onClick = doOnClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
    ) {
        if (!isLoading) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.onPrimary)
            )
        } else {
            LoadingDots(dotSize = 8.dp, color = MaterialTheme.colors.onPrimary)
        }
    }
}
