package com.example.keeptodo.navgation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import com.example.keeptodo.room.*
import com.example.keeptodo.screen.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(state: ContactState, onEvent: (ContactEvent) -> Unit,historyState: HistoryContactState, onHistoryEvent: (HistoryContactEvent) -> Unit) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController, startDestination = "HomeScreen",
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(400)) },
        popEnterTransition = { fadeIn(animationSpec = tween(400)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
    ) {
        composable(
            "HomeScreen",
            exitTransition = { fadeOut(animationSpec = tween(300, 200)) },
        ) {
            HomeScreen(navController, state, onEvent,onHistoryEvent)
        }
        composable("PlanScreen",
            enterTransition = { fadeIn(animationSpec = tween(200, 300)) },
            popExitTransition = { slideOutVertically(animationSpec = tween(500), targetOffsetY = { it }) }
        ) {
            PlanScreen(navController, state, onEvent)
        }
        composable("AllNoteScreen",
            enterTransition = { slideInHorizontally(animationSpec = tween(500), initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { -it }) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) }
        ) {
            AllNoteScreen(navController, state, onEvent,onHistoryEvent)
        }
        composable(
            "EditNoteScreen",
            popExitTransition = { fadeOut(animationSpec = tween(200, 200)) },
        ) {
            EditNoteScreen(navController, state, onEvent)
        }
        composable("CalenderScreen",
            enterTransition = { slideInVertically(animationSpec = tween(500), initialOffsetY = {-it})},
            popExitTransition = { slideOutVertically(animationSpec = tween(500), targetOffsetY = { -it }) }
        ) {
            CalenderScreen(navController,historyState)
        }
    }
}