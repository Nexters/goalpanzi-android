package com.goalpanzi.mission_mate.feature.onboarding.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButton
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.localDateToMillis
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate?,
    selectableStartDate: LocalDate?,
    selectableEndDate: LocalDate?,
    onSuccess: (Long) -> Unit,
    onDismiss: () -> Unit,
    initialDisplayedMonthMillis : Long? = null,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.let { localDateToMillis(it) },
        initialDisplayedMonthMillis = initialDisplayedMonthMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val startMillis = localDateToMillis(selectableStartDate)
                val endMillis = localDateToMillis(selectableEndDate)
                val currentMillis = utcTimeMillis
                return (startMillis ?: Long.MIN_VALUE) <= currentMillis && (endMillis
                    ?: Long.MAX_VALUE) >= currentMillis
            }
        }
    )
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DatePicker(state = datePickerState)
                MissionMateButton(
                    buttonType = if (datePickerState.selectedDateMillis == null) MissionMateButtonType.DISABLED
                    else MissionMateButtonType.SECONDARY,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onSuccess(it) }
                    }
                ) {
                    Text(text = "Ok")
                }
            }
        }
    }
}
