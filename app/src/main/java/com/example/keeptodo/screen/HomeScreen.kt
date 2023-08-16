package com.example.keeptodo.screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.keeptodo.item.DailyNoteItem
import com.example.keeptodo.room.ContactEvent
import com.example.keeptodo.room.ContactState
import com.example.keeptodo.ui.theme.BackGround
import com.example.keeptodo.ui.theme.Color1
import com.example.keeptodo.ui.theme.ExtendedColor
import com.example.keeptodo.ui.theme.ExtendedFontColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "UnusedTransitionTargetStateParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Surface(color = BackGround, modifier = Modifier.fillMaxSize()) {
        Box {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            Scaffold(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                containerColor = BackGround,
                contentColor = BackGround,
                topBar = {
                    MediumTopAppBar(
                        title = { Text(text = "KeepTODO今日計畫", Modifier.padding(start = 10.dp)) },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigate("AllNoteScreen")
                            }, modifier = Modifier.padding(start = 10.dp)) {
                                Icon(
                                    Icons.Default.FormatListBulleted,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = BackGround,
                            scrolledContainerColor = BackGround
                        ),
                    )
                }) { values ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(values)
                ) {
                    DailyNoteItem(state, onEvent, onEdit = {
                        if (it) navController.navigate("EditNoteScreen")
                    })
                }
            }
            NewButton(navController)
        }
    }
}

@Composable
fun NewButton(navController: NavController) {
    var isOpen by remember {
        mutableStateOf(false)
    }
    val height by animateIntAsState(targetValue = if (isOpen) 900 else 64, label = "", animationSpec = tween(500))
    val width by animateIntAsState(targetValue = if (isOpen) 450 else 140, label = "", animationSpec = tween(400))
    val pad by animateIntAsState(targetValue = if (isOpen) 0 else 25, label = "", animationSpec = tween(500))
    val color by animateColorAsState(
        targetValue = if (isOpen) BackGround else ExtendedColor,
        label = "",
        animationSpec = tween(500)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        Row {
            ExtendedFloatingActionButton(
                onClick = {
                    isOpen = true
                    navController.navigate("PlanScreen")
                },
                icon = {
                    Icon(
                        Icons.Default.Add,
                        "制定未来计划",
                        tint = ExtendedFontColor,
                        modifier = Modifier.size(30.dp)
                    )
                },
                text = { Text(text = "新增计划", color = ExtendedFontColor, fontSize = 15.sp) },
                containerColor = color,
                modifier = Modifier.size(width.dp, height.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 3.dp,
                    pressedElevation = 0.dp
                )
            )
            Spacer(modifier = Modifier.width(pad.dp))
        }
        Spacer(modifier = Modifier.height(pad.dp))
    }
}
