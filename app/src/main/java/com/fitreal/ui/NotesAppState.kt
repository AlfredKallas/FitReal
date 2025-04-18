package com.fitreal.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.fitreal.ui.utils.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class NotesAppState(
    private val snackbarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { message ->
                snackbarHostState.showSnackbar(message)
                snackbarManager.clearSnackbarState()
            }
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: Any) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: Any, popUp: Any) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: Any) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}