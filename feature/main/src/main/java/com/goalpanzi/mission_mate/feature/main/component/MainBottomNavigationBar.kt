package com.goalpanzi.mission_mate.feature.main.component

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.goalpanzi.mission_mate.core.designsystem.ext.clickableWithoutRipple
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.core.navigation.RouteModel.MainTabRoute
import com.goalpanzi.mission_mate.feature.main.R

internal const val MAIN_BOTTOM_NAVIGATION_BAR_HEIGHT = 48

enum class MainTab(
    val routeModel: RouteModel,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
) {
    MISSION(
        routeModel = MainTabRoute.MissionRouteModel.Onboarding(),
        labelRes = R.string.main_tab_onboarding,
        iconRes = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_flag
    ),
    HISTORY(
        routeModel = MainTabRoute.HistoryRouteModel.History,
        labelRes = R.string.main_tab_history,
        iconRes = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_time
    ),
    SETTING(
        routeModel = MainTabRoute.SettingRouteModel.Setting,
        labelRes = R.string.main_tab_setting,
        iconRes = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_setting
    );

    fun isSelected(destination: NavDestination?) : Boolean {
        val currentDestinationRoute = destination?.route ?: return false
        return containsWithRoute(currentDestinationRoute)
    }

    private fun containsWithRoute(route: String) : Boolean {
        val path = when(this){
            MISSION -> MainTabRoute.MissionRouteModel::class.qualifiedName
            HISTORY -> MainTabRoute.HistoryRouteModel::class.qualifiedName
            SETTING -> MainTabRoute.SettingRouteModel::class.qualifiedName
        } ?: return false
        return route.contains(path)
    }
}

@Composable
fun MainBottomNavigationBar(
    currentDestination: NavDestination?,
    onTabClick : (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Surface(
        modifier = modifier.height(MAIN_BOTTOM_NAVIGATION_BAR_HEIGHT.dp),
        color = Color((context as Activity).window.navigationBarColor)
    ) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = ColorGray4_FFE5E5E5
        )
        Row {
            MainTab.entries.forEach { mainTab ->
                val selected = mainTab.isSelected(currentDestination)
                MainBottomNavigationBarItem(
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            modifier = Modifier.height(24.dp),
                            imageVector = ImageVector.vectorResource(mainTab.iconRes),
                            contentDescription = mainTab.name,
                            tint = if(selected) ColorOrange_FFFF5732 else ColorGray1_FF404249
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(mainTab.labelRes),
                            style = MissionMateTypography.body_sm_regular.copy(fontSize = 10.sp),
                            color = if(selected) ColorOrange_FFFF5732 else ColorGray1_FF404249
                        )
                    },
                    onClick = {
                        onTabClick(mainTab)
                    }
                )
            }
        }
    }
}

@Composable
fun MainBottomNavigationBarItem(
    icon: @Composable () -> Unit,
    label : @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickableWithoutRipple {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon()
        label()
    }
}

