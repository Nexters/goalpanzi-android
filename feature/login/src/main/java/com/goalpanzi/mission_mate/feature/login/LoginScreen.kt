package com.goalpanzi.mission_mate.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorFFF5EDEA
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.Color_FFFF5632
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    onLoginSuccess: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                LoginEvent.Error -> Unit
                is LoginEvent.Success -> onLoginSuccess(it.isProfileSet)
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        onGoogleLoginClick = { viewModel.request(context) }
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onGoogleLoginClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color_FFFF5632)
                .navigationBarsPadding()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .size(48.dp),
                painter = painterResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_app_logo),
                contentDescription = "rabbit"
            )

            Image(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .width(266.dp)
                    .wrapContentHeight(),
                painter = painterResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_app_title),
                contentDescription = "rabbit",
                contentScale = ContentScale.FillWidth
            )

            Box(
                contentAlignment = Alignment.BottomCenter
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth(220f/390f)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.img_login_rabbit),
                    contentDescription = "rabbit",
                    contentScale = ContentScale.FillWidth
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(342f / 390f)
                        .padding(top = 175.dp)
                        .height(60.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp))
                        .clickable(onClick = onGoogleLoginClick),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_google),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.google_login),
                        modifier = modifier.fillMaxWidth(),
                        style = MissionMateTypography.body_lg_bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.login_social),
                style = MissionMateTypography.body_sm_regular,
                color = ColorWhite_FFFFFFFF
            )
            Image(
                modifier = modifier.fillMaxWidth(),
                painter = painterResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_login_bottom_animals),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onGoogleLoginClick = {}
    )
}
