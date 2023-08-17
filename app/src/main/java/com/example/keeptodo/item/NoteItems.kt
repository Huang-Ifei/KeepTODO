package com.example.keeptodo.item

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keeptodo.room.*
import com.example.keeptodo.ui.theme.*
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNoteItems(
    onEvent: (ContactEvent) -> Unit,
    state: ContactState,
    paddingValues: PaddingValues,
    onEdit: (Boolean) -> Unit,
) {
    ContactEvent.SortContact(sortType = SortType.DATE)
    LazyColumn(Modifier.padding(paddingValues)) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(state.contacts) { contact ->
            val cardColor = when (contact.color) {
                1 -> Green40
                2 -> Blue40
                3 -> Purple40
                else -> Color.Transparent
            }
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp, bottom = 12.dp),
                onClick = {
                    editNum = contact.id
                    onEdit(true)
                },
                border = BorderStroke(width = 1.dp, color = GreenBorder)
            ) {
                AllNoteItem(onEvent, contact, cardColor)
            }
        }
    }
    if (state.contacts.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .padding(paddingValues)
        ) {
            Text(text = "请添加一个计划", color = Color.DarkGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyNoteItems(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    paddingValues: PaddingValues,
    onEdit: (Boolean) -> Unit,
) {
    val date by remember {
        mutableStateOf(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()))
    }
    ContactEvent.SortContact(sortType = SortType.DATE)
    LazyColumn(Modifier.padding(paddingValues)) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(state.contacts) { contact ->
            if (date == contact.date) {
                val cardColor = when (contact.color) {
                    1 -> Green40
                    2 -> Blue40
                    3 -> Purple40
                    else -> Color.Transparent
                }
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .padding(start = 22.dp, end = 22.dp, bottom = 12.dp),
                    onClick = {
                        editNum = contact.id
                        onEdit(true)
                    },
                    border = BorderStroke(width = 1.dp, color = GreenBorder)
                ) {
                    DailyNoteItem(onEvent, contact, cardColor)
                }
            }
        }
        item { Spacer(modifier = Modifier.height(90.dp)) }
    }
    //当日暂无计划UI
    if (noDailyItem(state)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .padding(paddingValues)
        ) {
            Text(text = "今日暂无计划", color = Color.DarkGray)
        }
    }
}

//当日是否无计划逻辑
@Composable
fun noDailyItem(state: ContactState): Boolean {
    val date by remember {
        mutableStateOf(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()))
    }
    for (contact in state.contacts) {
        if (contact.date == date) return false
    }
    return true
}