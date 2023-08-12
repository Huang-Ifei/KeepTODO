package com.example.keeptodo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.keeptodo.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController) {
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize(), color = Color1) {
        var pickedDate by remember {
            mutableStateOf(LocalDate.parse(arrString[1][editNum]))
        }
        var formatDate by remember {
            mutableStateOf(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(pickedDate))
        }
        val dateDialogState = rememberMaterialDialogState()
        var things by remember {
            mutableStateOf(arrString[2][editNum])
        }
        var indicatorColor by remember {
            mutableStateOf(Color.Gray)
        }
        var state by remember { mutableStateOf(arrString[3][editNum].toInt()) }
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
                            Row() {
                                Spacer(modifier = Modifier.width(25.dp))
                                Text(text = "修改计划", fontSize = 20.sp)

                            }
                            Divider(Modifier.padding(10.dp))
                        }

                        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Row() {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(25.dp))
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }
                    }
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = things,
                            onValueChange = { things = it },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                cursorColor = Color.Black,
                                containerColor = BackGround,
                                focusedIndicatorColor = indicatorColor,
                                unfocusedIndicatorColor = indicatorColor,
                                focusedLabelColor = indicatorColor,
                                selectionColors = TextSelectionColors(Color.Black, Color.Black)
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
                            selected = state == 1,
                            onClick = { state = 1 },
                            colors = RadioButtonDefaults.colors(selectedColor = MainColor)
                        )
                        Text(text = "绿色", fontSize = 18.sp, modifier = Modifier.clickable { state = 1 })
                        Spacer(modifier = Modifier.width(5.dp))
                        RadioButton(
                            selected = state == 2,
                            onClick = { state = 2 },
                            colors = RadioButtonDefaults.colors(selectedColor = Blue40)
                        )
                        Text(text = "蓝色", fontSize = 18.sp, modifier = Modifier.clickable { state = 2 })
                        Spacer(modifier = Modifier.width(5.dp))
                        RadioButton(
                            selected = state == 3,
                            onClick = { state = 3 },
                            colors = RadioButtonDefaults.colors(selectedColor = Purple40)
                        )
                        Text(text = "紫色", fontSize = 18.sp, modifier = Modifier.clickable { state = 3 })
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                if (LocalDate.now() == LocalDate.parse(arrString[1][editNum])) daily--
                                for (i in editNum until n) {
                                    arrString[0][i] = arrString[0][i + 1]
                                    arrString[1][i] = arrString[1][i + 1]
                                    arrString[2][i] = arrString[2][i + 1]
                                    arrString[3][i] = arrString[3][i + 1]
                                }
                                saveArrayToSharedPreferences(context, "data")
                                n--
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ExtendedColor)
                        ) {
                            Text(text = "删除计划", fontSize = 17.sp, color = ExtendedFontColor)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                if (things.isEmpty() || things.isBlank()) {
                                    indicatorColor = Color.Red
                                } else {
                                    arrString[1][editNum] = pickedDate.toString()
                                    arrString[2][editNum] = things
                                    arrString[3][editNum] = state.toString()
                                    saveArrayToSharedPreferences(context, "data")
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
                        onClick = { formatDate = DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(pickedDate) },
                        textStyle = TextStyle(color = MainColor)
                    )
                },
            ) {
                datepicker(
                    initialDate = pickedDate, title = "选择日期", colors = DatePickerDefaults.colors(
                        headerBackgroundColor = MainColor,
                        headerTextColor = BackGround,
                        dateActiveBackgroundColor = MainColor
                    )
                ) {
                    pickedDate = it
                }
            }
        }

    }
}