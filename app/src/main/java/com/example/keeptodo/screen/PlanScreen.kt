package com.example.keeptodo.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.keeptodo.room.ContactEvent
import com.example.keeptodo.room.ContactState
import com.example.keeptodo.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun initialization(onEvent: (ContactEvent) -> Unit) {
    onEvent(ContactEvent.SetDate(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now())))
    onEvent(ContactEvent.SetContext(""))
    onEvent(ContactEvent.SetColor(1))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(navController: NavController, state: ContactState, onEvent: (ContactEvent) -> Unit) {
    var set by remember {
        mutableStateOf(initialization(onEvent))
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background, shadowElevation = 50.dp) {
        val dateDialogState = rememberMaterialDialogState()
        var isError by remember {
            mutableStateOf(false)
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                Modifier
                    .height(450.dp)
                    .fillMaxWidth(0.82f), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                border = BorderStroke(width = 1.dp, color = GreenBorder)
            ) {
                Column(Modifier.fillMaxSize()) {
                    Box {
                        Column {
                            Spacer(modifier = Modifier.height(25.dp))
                            Row {
                                Spacer(modifier = Modifier.width(25.dp))
                                Text(
                                    text = "添加一项计划",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )

                            }
                        }

                        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Row {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }
                    }
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = state.context,
                            onValueChange = { onEvent(ContactEvent.SetContext(it)) },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                selectionColors = TextSelectionColors(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.onSecondary)
                            ),
                            label = { Text(text = "内容") },
                            maxLines = 4,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 18.sp),
                            isError = isError
                        )
                    }
                    //日期处理
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(
                            text = "计划日期:",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = state.date,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.clickable { dateDialogState.show() })
                    }
                    //颜色处理
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(12.dp))
                        RadioButton(
                            selected = state.color == 1,
                            onClick = { onEvent(ContactEvent.SetColor(1)) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Green40,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            text = "绿色",
                            fontSize = 18.sp,
                            modifier = Modifier.clickable { onEvent(ContactEvent.SetColor(1)) },
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        RadioButton(
                            selected = state.color == 2,
                            onClick = { onEvent(ContactEvent.SetColor(2)) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue40,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            text = "蓝色",
                            fontSize = 18.sp,
                            modifier = Modifier.clickable { onEvent(ContactEvent.SetColor(2)) },
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        RadioButton(
                            selected = state.color == 3,
                            onClick = { onEvent(ContactEvent.SetColor(3)) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Purple40,
                                unselectedColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                        Text(
                            text = "紫色",
                            fontSize = 18.sp,
                            modifier = Modifier.clickable { onEvent(ContactEvent.SetColor(3)) },
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    //确认
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                if (state.context.isBlank()) {
                                    isError = true
                                } else {
                                    onEvent(ContactEvent.SaveContact)
                                    navController.popBackStack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        ) {
                            Text(text = "添加计划", fontSize = 17.sp, color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Spacer(modifier = Modifier.width(25.dp))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {

                    }
                }
            }

            //选择日期
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(
                        text = "确认",
                        onClick = { },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.surface
            ) {
                datepicker(
                    initialDate = LocalDate.now(), title = "选择日期", colors = DatePickerDefaults.colors(
                        headerBackgroundColor = MaterialTheme.colorScheme.primary,
                        headerTextColor = MaterialTheme.colorScheme.surface,
                        dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                        calendarHeaderTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        dateActiveTextColor = MaterialTheme.colorScheme.onPrimary,
                        dateInactiveTextColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    onEvent(ContactEvent.SetDate(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(it)))
                }
            }
        }
    }
}
