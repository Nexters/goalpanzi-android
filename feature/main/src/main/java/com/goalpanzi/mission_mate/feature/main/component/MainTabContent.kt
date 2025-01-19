package com.goalpanzi.mission_mate.feature.main.component

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.navigation.model.MainTabDataModel

@Composable
fun MainTabContent(
    navigator: MainNavigator,
    mainTabDataModel: MainTabDataModel,
    modifier: Modifier = Modifier,
    mainTabNavigator: MainTabNavigator = rememberMainTabNavigator()
) {

    val backStackEntryState =
        mainTabNavigator.navController.currentBackStackEntryFlow.collectAsState(initial = null)
    val navigationBottomPadding =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LaunchedEffect(mainTabDataModel) {
        Log.d("123123","${mainTabDataModel}")
        when (mainTabDataModel) {
            is MainTabDataModel.Mission -> {
                mainTabNavigator.navigationToOnboarding(
                    mainTabDataModel.isAfterProfileCreate
                )
            }

            MainTabDataModel.History -> {

            }

            MainTabDataModel.Setting -> {
                mainTabNavigator.navigationToSetting()
            }

            MainTabDataModel.None -> {

            }
        }
    }

    Scaffold(
        modifier = modifier.padding(bottom = navigationBottomPadding),
        bottomBar = {
            MainBottomNavigationBar(
                modifier = Modifier.height(MAIN_BOTTOM_NAVIGATION_BAR_HEIGHT.dp),
                currentDestination = backStackEntryState.value?.destination,
                onTabClick = { mainTab ->
                    when (mainTab) {
                        MainTab.MISSION -> {
                            mainTabNavigator.navigationToOnboarding()
                        }

                        MainTab.HISTORY -> {

                        }

                        MainTab.SETTING -> {
                            mainTabNavigator.navigationToSetting()
                        }
                    }
                }
            )
        },
        content = { padding ->
            MainTabNavHost(
                mainNavigator = navigator,
                mainTabNavigator = mainTabNavigator
            )
        }
    )
}
