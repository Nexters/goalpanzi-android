package com.goalpanzi.mission_mate.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                is LoginEvent.Success -> onLoginSuccess(it.isAlreadyMember)
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
                .background(color = ColorFFF5EDEA)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = modifier
                    .size(342.dp)
            )

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 91.dp)
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
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onGoogleLoginClick = {}
    )
}
