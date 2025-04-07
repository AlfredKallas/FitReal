package com.fitreal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitreal.ui.screens.addworkout.AddWorkoutScreen
import com.fitreal.ui.screens.authentication.sign_in.SignInScreen
import com.fitreal.ui.screens.authentication.sign_up.SignUpScreen
import com.fitreal.ui.screens.profile.ProfileScreen
import com.fitreal.ui.screens.splash.SplashScreen
import com.fitreal.ui.screens.workoutsfeed.WorkoutFeedScreen
import com.fitreal.ui.theme.FitRealTheme
import com.fitreal.ui.utils.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable

@Serializable
data object SplashScreen

@Serializable
data object SignInScreen

@Serializable
data object SignUpScreen

@Serializable
data object ProfileScreen

@Serializable
data object AddNewWorkoutScreen

@Serializable
data object  WorkoutsScreen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FitRealApp() {
    FitRealTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val snackbarHostState = remember { SnackbarHostState() }
            val appState = rememberAppState(snackbarHostState)

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SplashScreen,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    fitRealGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): NotesAppState {
    return remember(snackbarHostState, navController, snackbarManager, coroutineScope) {
        NotesAppState(snackbarHostState, navController, snackbarManager, coroutineScope)
    }
}

fun NavGraphBuilder.fitRealGraph(appState: NotesAppState) {

        composable<SplashScreen> {
            SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
        }

        composable<SignInScreen> {
            SignInScreen(
                openScreen = { route -> appState.navigate(route) },
                openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
            )
        }

        composable<SignUpScreen> {
            SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
        }

        composable<ProfileScreen> {
            ProfileScreen(openScreen = { route -> appState.navigate(route) })
        }

        composable<AddNewWorkoutScreen> {
            AddWorkoutScreen(popBackStack = { appState.popUp() })
        }

        composable<WorkoutsScreen> {
            WorkoutFeedScreen(openScreen = { route -> appState.navigate(route) })
        }
}

