package com.example.keeptodo.navgation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import com.example.keeptodo.room.Contact
import com.example.keeptodo.room.ContactEvent
import com.example.keeptodo.room.ContactState
import com.example.keeptodo.screen.AllNoteScreen
import com.example.keeptodo.screen.EditNoteScreen
import com.example.keeptodo.screen.HomeScreen
import com.example.keeptodo.screen.PlanScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(state: ContactState, onEvent: (ContactEvent) -> Unit) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController, startDestination = "HomeScreen",
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(400))},
        popEnterTransition = { fadeIn(animationSpec = tween(400)) },
        popExitTransition = { fadeOut(animationSpec = tween(300))},
    ) {
        composable(
            "HomeScreen",
            exitTransition = { fadeOut(animationSpec = tween(300, 200)) },
        ) {
            HomeScreen(navController, state, onEvent)
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
            AllNoteScreen(navController, state, onEvent)
        }
        composable("EditNoteScreen") {
            EditNoteScreen(navController, state, onEvent)
        }
    }
}