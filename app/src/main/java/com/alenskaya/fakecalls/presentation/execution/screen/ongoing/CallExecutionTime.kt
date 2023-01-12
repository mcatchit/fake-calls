package com.alenskaya.fakecalls.presentation.execution.screen.ongoing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alenskaya.fakecalls.R
import com.alenskaya.fakecalls.presentation.execution.screen.calling.SmallTitle
import com.alenskaya.fakecalls.presentation.theme.CallingGreen

/**
 * Call execution time
 * @param time - time value.
 * @param description - description for icon.
 * @param modifier - modifier.
 */
@Composable
fun CallExecutionTime(
    time: String,
    description: String,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.call_time_icon),
            contentDescription = description,
            modifier = Modifier
                .size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        SmallTitle(text = time, color = CallingGreen)
    }
}
