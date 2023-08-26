package com.example.keeptodo.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import com.example.keeptodo.room.*
import com.example.keeptodo.ui.theme.GreenBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyNoteItem(
    onEvent: (ContactEvent) -> Unit,
    contact: Contact,
    cardColor: Color,
    onHistoryEvent: (HistoryContactEvent) -> Unit
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .graphicsLayer {
                alpha = animatedAlpha
            }
    ) {
        println(contact)

        Card(
            Modifier
                .size(35.dp, 44.dp)
                .padding(start = 28.dp, top = 37.dp), colors = CardDefaults.cardColors(cardColor)
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
                            onHistoryEvent(HistoryContactEvent.SetContext(contact.context))
                            onHistoryEvent(HistoryContactEvent.SetDate(contact.date))
                            onHistoryEvent(HistoryContactEvent.SetColor(contact.color))
                            onHistoryEvent(
                                HistoryContactEvent.SetMonth(
                                    DateTimeFormatter.ofPattern("yyyy年MM月").format(
                                        LocalDate.parse(
                                            contact.date,
                                            DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                                        )
                                    )
                                )
                            )
                            delay(400)
                            onHistoryEvent(HistoryContactEvent.SaveContact)
                            onEvent(ContactEvent.DeleteContact(contact))
                            visible = true
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
            Text(
                text = "   ${contact.context}",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }

}
