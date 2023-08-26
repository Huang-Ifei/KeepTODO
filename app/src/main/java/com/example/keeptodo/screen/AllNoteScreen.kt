package com.example.keeptodo.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.keeptodo.item.AllNoteItems
import com.example.keeptodo.room.ContactEvent
import com.example.keeptodo.room.ContactState
import com.example.keeptodo.room.HistoryContactEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNoteScreen(
    navController: NavController,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    onHistoryEvent : (HistoryContactEvent) -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize(), shadowElevation = 50.dp) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.background,
            topBar = {
                MediumTopAppBar(
                    title = { Text(text = "KeepTODO所有計畫", Modifier.padding(10.dp)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }, modifier = Modifier.padding(start = 10.dp)) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background
                    ),
                    actions = {
                        IconButton(onClick = {
                            navController.navigate("CalenderScreen")
                        }, modifier = Modifier.padding(end = 10.dp)) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                )
            }) { values ->
            AllNoteItems(onEvent, state, values, onEdit = {
                if (it) navController.navigate("EditNoteScreen")
            },onHistoryEvent)
        }
    }
}
