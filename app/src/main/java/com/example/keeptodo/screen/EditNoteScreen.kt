package com.example.keeptodo.screen

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
import com.example.keeptodo.room.Contact
import com.example.keeptodo.room.ContactEvent
import com.example.keeptodo.room.ContactState
import com.example.keeptodo.room.editNum
import com.example.keeptodo.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//通过id来寻找contact
fun whichContact(state: ContactState): Contact {
    for (contact in state.contacts) {
        if (contact.id == editNum) {
            return contact
        }
    }
    return TODO("提供返回值")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, state: ContactState, onEvent: (ContactEvent) -> Unit) {
    val contactSelect by remember {
        mutableStateOf(whichContact(state))
    }
    var context by remember {
        mutableStateOf(contactSelect.context)
    }
    var indicatorColor by remember {
        mutableStateOf(Color.Gray)
    }
    var colorSelect by remember {
        mutableStateOf(contactSelect.color)
    }
    var dateSelect by remember {
        mutableStateOf(LocalDate.parse(contactSelect.date, DateTimeFormatter.ofPattern("yyyy年MM月dd日")))
    }
    var formatDate by remember {
        mutableStateOf(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(dateSelect))
    }
    onEvent(ContactEvent.SetDate(formatDate))
    onEvent(ContactEvent.SetContext(context))
    onEvent(ContactEvent.SetColor(colorSelect))
    val dateDialogState = rememberMaterialDialogState()
    Surface(modifier = Modifier.fillMaxSize(), color = Color1) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                Modifier
                    .height(450.dp)
                    .fillMaxWidth(0.82f), colors = CardDefaults.cardColors(BackGround)
            ) {
                Column(Modifier.fillMaxSize()) {
                    Box {
                        Column {
                            Spacer(modifier = Modifier.height(25.dp))
                            Row {
                                Spacer(modifier = Modifier.width(25.dp))
                                Text(text = "修改计划", fontSize = 20.sp)

                            }
                            Divider(Modifier.padding(10.dp))
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
                            value = context,
                            onValueChange = {
                                context = it
                                onEvent(ContactEvent.SetContext(context))
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                cursorColor = MainColor,
                                containerColor = BackGround,
                                focusedIndicatorColor = indicatorColor,
                                unfocusedIndicatorColor = indicatorColor,
                                focusedLabelColor = indicatorColor,
                                selectionColors = TextSelectionColors(MainColor, Color.LightGray)
                            ),
                            label = { Text(text = "内容") },
                            maxLines = 4,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(24.dp))
                        Text(text = "计划日期:", fontSize = 18.sp)
                        Text(
                            text = formatDate,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.clickable { dateDialogState.show() })
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(12.dp))
                        RadioButton(
                            selected = colorSelect == 1,
                            onClick = {
                                colorSelect = 1
                                onEvent(ContactEvent.SetColor(colorSelect))
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = MainColor)
                        )
                        Text(text = "绿色", fontSize = 18.sp, modifier = Modifier.clickable {
                            colorSelect = 1
                            onEvent(ContactEvent.SetColor(colorSelect))
                        })
                        Spacer(modifier = Modifier.width(5.dp))
                        RadioButton(
                            selected = colorSelect == 2,
                            onClick = {
                                colorSelect = 2
                                onEvent(ContactEvent.SetColor(colorSelect))
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = Blue40)
                        )
                        Text(text = "蓝色", fontSize = 18.sp, modifier = Modifier.clickable {
                            colorSelect = 2
                            onEvent(ContactEvent.SetColor(colorSelect))
                        })
                        Spacer(modifier = Modifier.width(5.dp))
                        RadioButton(
                            selected = colorSelect == 3,
                            onClick = {
                                colorSelect = 3
                                onEvent(ContactEvent.SetColor(colorSelect))
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = Purple40)
                        )
                        Text(text = "紫色", fontSize = 18.sp, modifier = Modifier.clickable {
                            colorSelect = 3
                            onEvent(ContactEvent.SetColor(colorSelect))
                        })
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                onEvent(ContactEvent.DeleteContact(contactSelect))
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ExtendedColor)
                        ) {
                            Text(text = "删除计划", fontSize = 17.sp, color = ExtendedFontColor)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                if (context.isBlank()) {
                                    indicatorColor = Color.Red
                                } else {
                                    onEvent(ContactEvent.DeleteContact(contactSelect))
                                    onEvent(ContactEvent.SaveContact)
                                    navController.popBackStack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(MainColor),
                        ) {
                            Text(text = "保存计划", fontSize = 17.sp, color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(25.dp))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {

                    }
                }
            }
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(
                        text = "确认",
                        onClick = { },
                        textStyle = TextStyle(color = MainColor)
                    )
                },
            ) {
                datepicker(
                    initialDate = dateSelect, title = "选择日期", colors = DatePickerDefaults.colors(
                        headerBackgroundColor = MainColor,
                        headerTextColor = BackGround,
                        dateActiveBackgroundColor = MainColor
                    )
                ) {
                    dateSelect = it
                    formatDate = DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(dateSelect)
                    onEvent(ContactEvent.SetDate(formatDate))
                }
            }
        }

    }
}