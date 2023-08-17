@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.keeptodo.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keeptodo.room.*
import com.example.keeptodo.ui.theme.*
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AllNoteItem(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    onEdit: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    ContactEvent.SortContact(sortType = SortType.DATE)
    LazyColumn() {
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
                println(contact)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Card(
                        Modifier
                            .size(35.dp, 36.dp)
                            .padding(start = 28.dp, top = 29.dp), colors = CardDefaults.cardColors(cardColor)) {

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .align(Alignment.BottomEnd),
                        horizontalAlignment = Alignment.End,
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    withContext(Dispatchers.IO) {
                                        delay(500)
                                        onEvent(ContactEvent.DeleteContact(contact))
                                    }
                                }
                            },
                            Modifier
                                .padding(end = 8.dp, bottom = 13.dp)
                                .fillMaxHeight()
                                .clickable() { },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(
                            top = 20.dp,
                            start = 25.dp,
                            end = 50.dp,
                            bottom = 20.dp
                        )
                    ) {
                        Row() {
                            Text(text = "   ${contact.context}", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                        Text(
                            text = contact.date,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
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
        ) {
            Text(text = "请添加一个计划", color = Color.DarkGray)
        }
    }
}

@Composable
fun DailyNoteItem(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    onEdit:(Boolean)->Unit
) {
    val date by remember {
        mutableStateOf(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()))
    }
    val coroutineScope = rememberCoroutineScope()
    ContactEvent.SortContact(sortType = SortType.DATE)
    LazyColumn() {
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
                    println(contact)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Card(
                            Modifier
                                .size(35.dp, 44.dp)
                                .padding(start = 28.dp, top = 37.dp), colors = CardDefaults.cardColors(cardColor)) {

                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .align(Alignment.BottomEnd),
                            horizontalAlignment = Alignment.End,
                        ) {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        withContext(Dispatchers.IO) {
                                            delay(500)
                                            onEvent(ContactEvent.DeleteContact(contact))
                                        }
                                    }
                                },
                                Modifier
                                    .padding(end = 8.dp, bottom = 15.dp)
                                    .fillMaxHeight()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.padding(
                                top = 28.dp,
                                start = 25.dp,
                                end = 50.dp,
                                bottom = 28.dp
                            )
                        ) {
                            Text(text = "   ${contact.context}", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
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
                .padding(bottom = 180.dp)
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