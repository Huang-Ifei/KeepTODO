package com.example.keeptodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
import com.example.keeptodo.ui.theme.BackGround
import java.time.LocalDate
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        loadArrayFromSharedPreferences(applicationContext, "data")

        try {
            for (i in 0..999) {
                if (arrString[1][i] == "") {
                    arrString[0][i] = ""
                } else if (arrString[0][i] == i.toString()) {
                    if (arrString[1][i] == LocalDate.now().toString()) daily++
                    n++;
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = BackGround) {
                        Navigation()
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "HomeScreen",
        enterTransition = { fadeIn(animationSpec = tween(300))},
        exitTransition = { fadeOut(animationSpec = tween(500))},
        popEnterTransition = { fadeIn(animationSpec = tween(300))},
        popExitTransition = { fadeOut(animationSpec = tween(500))},
        ) {
        composable("HomeScreen",
            exitTransition = {fadeOut(animationSpec = tween(300,200))},
            ) {
            HomeScreen(navController)
        }
        composable("PlanScreen",
            enterTransition = { fadeIn(animationSpec = tween(200,300))},
            popExitTransition = { slideOutVertically(animationSpec = tween(500), targetOffsetY = {it}) }
        ) {
            PlanScreen(navController)
        }
        composable("AllNoteScreen",
            enterTransition = { slideInHorizontally (animationSpec = tween(500), initialOffsetX = {-it})},
            popExitTransition = { slideOutHorizontally(animationSpec = tween(500), targetOffsetX = {-it})},
            popEnterTransition = {fadeIn(animationSpec = tween(300))}
            ) {
            AllNoteScreen(navController)
        }
        composable("EditNoteScreen",
            popExitTransition = {fadeOut(animationSpec = tween(500))},
            enterTransition = { fadeIn(animationSpec = tween(500))}){
            EditNoteScreen(navController)
        }
    }
}