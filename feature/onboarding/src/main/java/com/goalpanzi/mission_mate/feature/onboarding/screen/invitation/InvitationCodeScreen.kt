package com.goalpanzi.mission_mate.feature.onboarding.screen.invitation

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorRed_FFFF5858
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.component.InvitationCodeTextField
import com.goalpanzi.mission_mate.feature.onboarding.model.CodeResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.MissionUiModel
import com.goalpanzi.mission_mate.feature.onboarding.screen.invitation.InvitationCodeViewModel.Companion.CodeActionEvent
import com.goalpanzi.mission_mate.feature.onboarding.util.styledTextWithHighlights
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InvitationCodeRoute(
    onBackClick: () -> Unit,
    viewModel: InvitationCodeViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current

    val isNotCodeValid by viewModel.isNotCodeValid.collectAsStateWithLifecycle()
    val enabledButton by viewModel.enabledButton.collectAsStateWithLifecycle()

    var hasInvitationDialogData by remember { mutableStateOf<MissionUiModel?>(null) }

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.codeInputActionEvent.collect {
                when (it) {
                    CodeActionEvent.FIRST_DONE,
                    CodeActionEvent.SECOND_DONE,
                    CodeActionEvent.THIRD_DONE -> {
                        delay(80)
                        localFocusManager.moveFocus(FocusDirection.Next)
                    }

                    else -> {

                    }
                }
            }
        }

        launch {
            viewModel.codeResultEvent.collect { result ->
                when (result) {
                    is CodeResultEvent.Success -> {
                        hasInvitationDialogData = result.mission
                    }

                    is CodeResultEvent.Error -> {

                    }
                }
            }
        }
    }
    hasInvitationDialogData?.let { mission ->
        InvitationDialog(
            count = 13,
            missionTitle = mission.missionTitle,
            missionPeriod = mission.missionPeriod,
            missionDays = mission.missionDays,
            missionTime = mission.missionTime,
            onDismissRequest = {
                hasInvitationDialogData = null
            },
            onClickOk = {
                hasInvitationDialogData = null
            }
        )
    }
    InvitationCodeScreen(
        codeFirst = viewModel.codeFirst,
        codeSecond = viewModel.codeSecond,
        codeThird = viewModel.codeThird,
        codeFourth = viewModel.codeFourth,
        onCodeFirstChange = viewModel::updateCodeFirst,
        onCodeSecondChange = viewModel::updateCodeSecond,
        onCodeThirdChange = viewModel::updateCodeThird,
        onCodeFourthChange = viewModel::updateCodeFourth,
        onClickButton = {
            keyboardController?.hide()
            localFocusManager.clearFocus()
            viewModel.checkCode()
        },
        onBackClick = onBackClick,
        isNotCodeValid = isNotCodeValid,
        enabledButton = enabledButton
    )
}

@Composable
fun InvitationCodeScreen(
    codeFirst: String,
    codeSecond: String,
    codeThird: String,
    codeFourth: String,
    onCodeFirstChange: (String) -> Unit,
    onCodeSecondChange: (String) -> Unit,
    onCodeThirdChange: (String) -> Unit,
    onCodeFourthChange: (String) -> Unit,
    onClickButton: () -> Unit,
    onBackClick: () -> Unit,
    isNotCodeValid: Boolean,
    enabledButton: Boolean,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
            .verticalScroll(scrollState)
            .imePadding()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        MissionMateTopAppBar(
            modifier = modifier,
            navigationType = NavigationType.BACK,
            onNavigationClick = onBackClick,
            containerColor = ColorWhite_FFFFFFFF,
        )
        Text(
            text = stringResource(id = R.string.onboarding_invitation_title),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 22.dp),
            style = MissionMateTypography.heading_sm_bold,
            color = ColorGray1_FF404249
        )

        Text(
            text = styledTextWithHighlights(
                text = stringResource(id = R.string.onboarding_invitation_description),
                colorTargetTexts = listOf(stringResource(id = R.string.onboarding_invitation_description_color_target)),
                textColor = ColorGray2_FF4F505C,
                targetTextColor = ColorOrange_FFFF5732,
                targetFontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 54.dp),
            style = MissionMateTypography.title_xl_regular,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            InvitationCodeTextField(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                text = codeFirst,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isNotCodeValid,
                onValueChange = onCodeFirstChange
            )
            InvitationCodeTextField(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                text = codeSecond,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isNotCodeValid,
                onValueChange = onCodeSecondChange
            )
            InvitationCodeTextField(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                text = codeThird,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isNotCodeValid,
                onValueChange = onCodeThirdChange
            )
            InvitationCodeTextField(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                text = codeFourth,
                isError = isNotCodeValid,
                onValueChange = onCodeFourthChange
            )
        }
        if (isNotCodeValid) {
            val set = mutableSetOf<Int>()
            set.forEach {
                return@forEach
            }
            Text(
                modifier = Modifier.padding(top = 12.dp, start = 24.dp, end = 24.dp),
                text = stringResource(id = R.string.onboarding_invitation_error),
                style = MissionMateTypography.body_md_regular,
                color = ColorRed_FFFF5858
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        MissionMateTextButton(
            modifier = Modifier
                .padding(vertical = 36.dp, horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            buttonType = if (enabledButton) MissionMateButtonType.ACTIVE else MissionMateButtonType.DISABLED,
            textId = R.string.confirm,
            onClick = onClickButton
        )
    }

}