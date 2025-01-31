package com.goalpanzi.mission_mate.feature.setting.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.component.NavigationType
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.setting.Event
import com.goalpanzi.mission_mate.feature.setting.Util
import com.goalpanzi.mission_mate.feature.setting.Util.presentNotificationSetting
import com.luckyoct.feature.setting.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingRoute(
    modifier: Modifier = Modifier,
    onClickProfileSetting: () -> Unit,
    onClickServicePolicy: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onBackClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val showLogoutDialog = remember { mutableStateOf(false) }
    val showDeleteAccountDialog = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                Event.GoToLogin -> {
                    showLogoutDialog.value = false
                    onLogout()
                }
            }
        }
    }

    if (showLogoutDialog.value) {
        LogoutDialog(
            onDismissRequest = { showLogoutDialog.value = false },
            onClickOk = { viewModel.logout() }
        )
    }

    if (showDeleteAccountDialog.value) {
        AccountDeleteDialog(
            onDismissRequest = { showDeleteAccountDialog.value = false },
            onClickOk = { viewModel.deleteAccount() }
        )
    }

    SettingScreen(
        modifier = modifier,
        onBackClick = { onBackClick() },
        onClickProfileSetting = { onClickProfileSetting() },
        onClickNotification = { presentNotificationSetting(context) },
        onClickServicePolicy = { onClickServicePolicy() },
        onClickPrivacyPolicy = { onClickPrivacyPolicy() },
        onClickLogout = { showLogoutDialog.value = true },
        onClickDeleteAccount = { showDeleteAccountDialog.value = true }
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onClickProfileSetting: () -> Unit,
    onClickNotification: () -> Unit,
    onClickServicePolicy: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickLogout: () -> Unit,
    onClickDeleteAccount: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Text(
            text = stringResource(id = R.string.setting_title),
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 24.dp,top = 20.dp, bottom = 10.dp),
            style = MissionMateTypography.heading_sm_bold,
            color = ColorGray1_FF404249
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            SettingHeader(titleRes = R.string.my_info)
            SettingContent(
                titleRes = R.string.setting_profile,
                onClick = { onClickProfileSetting() }
            )
            SettingContent(
                titleRes = R.string.setting_notification,
                onClick = { onClickNotification() }
            )
            Divider()
            SettingHeader(titleRes = R.string.version_info)
            SettingContent(
                titleRes = R.string.current_version,
                subContent = {
                    Text(
                        text = Util.getAppVersionName(LocalContext.current),
                        style = MissionMateTypography.body_xl_regular,
                        color = ColorGray1_FF404249
                    )
                }
            )
            Divider()
            SettingHeader(titleRes = R.string.help_desk)
            SettingContent(
                titleRes = R.string.inquiry,
                subContent = {
                    Text(
                        text = stringResource(id = R.string.inquiry_email),
                        style = MissionMateTypography.body_md_regular,
                        color = ColorGray1_FF404249
                    )
                }
            )
            Divider()
            SettingHeader(titleRes = R.string.policy)
            SettingContent(
                titleRes = R.string.service_policy,
                onClick = { onClickServicePolicy() }
            )
            SettingContent(
                titleRes = R.string.privacy_policy,
                onClick = { onClickPrivacyPolicy() }
            )
            Divider()
            SettingHeader(titleRes = R.string.login_info)
            SettingContent(
                titleRes = R.string.logout,
                onClick = { onClickLogout() }
            )
            SettingContent(
                titleRes = R.string.delete_account,
                onClick = { onClickDeleteAccount() }
            )
        }
    }
}

@Composable
fun SettingHeader(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = titleRes),
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, top = 18.dp),
        style = MissionMateTypography.body_lg_regular,
        color = ColorGray3_FF727484
    )
}

@Composable
fun SettingContent(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    subContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .run {
                onClick?.let {
                    clickable { it() }
                } ?: run { this }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = modifier.width(24.dp))
        Text(
            text = stringResource(id = titleRes),
            style = MissionMateTypography.body_xl_regular,
            color = ColorGray1_FF404249,
            modifier = modifier
                .wrapContentHeight()
                .weight(1f)
                .padding(vertical = 16.dp)
        )
        onClick?.let {
            Image(
                imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_arrow_right),
                contentDescription = ""
            )
        }
        subContent?.let {
            it()
        }
        Spacer(modifier = modifier.width(24.dp))
    }
}

@Composable
fun Divider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(ColorGray4_FFE5E5E5)
            .padding(bottom = 10.dp)
    )
}

@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit
) {
    MissionMateDialog(
        titleId = R.string.confirm_logout,
        onDismissRequest = onDismissRequest,
        onClickOk = onClickOk,
        okTextId = R.string.logout,
        cancelTextId = R.string.cancel
    )
}

@Composable
fun AccountDeleteDialog(
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit
) {
    MissionMateDialog(
        titleId = R.string.confirm_delete_account,
        onDismissRequest = onDismissRequest,
        descriptionId = R.string.confirm_delete_account_content,
        onClickOk = onClickOk,
        okTextId = R.string.require_delete_account,
        cancelTextId = R.string.cancel
    )
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        onBackClick = {},
        onClickProfileSetting = {},
        onClickNotification = {},
        onClickServicePolicy = {},
        onClickPrivacyPolicy = {},
        onClickLogout = {},
        onClickDeleteAccount = {}
    )
}
