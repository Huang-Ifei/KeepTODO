package com.example.keeptodo.item

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
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
import com.example.keeptodo.room.Contact
import com.example.keeptodo.room.ContactEvent
import com.example.keeptodo.room.ContactState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AllNoteItem(
    onEvent: (ContactEvent) -> Unit,
    contact: Contact,
    cardColor: Color,
) {
    var visible by remember {
        mutableStateOf(true)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha",
        animationSpec = tween(500)
    )
    val coroutineScope = rememberCoroutineScope()
    println(contact)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .graphicsLayer { alpha = animatedAlpha }
    ) {
        Card(
            Modifier
                .size(35.dp, 36.dp)
                .padding(start = 28.dp, top = 29.dp), colors = CardDefaults.cardColors(cardColor)
        ) {

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
                            visible = false
                            delay(400)
                            onEvent(ContactEvent.DeleteContact(contact))
                            visible = true
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
                Text(
                    text = "   ${contact.context}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Text(
                text = contact.date,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

