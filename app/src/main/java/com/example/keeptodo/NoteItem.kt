@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.keeptodo

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.keeptodo.ui.theme.*
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

var InClick = 0

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteItem(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formatDate by remember {
        mutableStateOf(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(pickedDate))
    }
    val i = (0 until n).toList()

    var re by remember {
        mutableStateOf(true)
    }
    if (re) {
        LazyColumn {
            item { Spacer(modifier = Modifier.height(10.dp)) }
            items(i) { i ->
                Card(colors = CardDefaults.cardColors(Color.Transparent)) {
                    val color = when (arrString[3][i]) {
                        "1" -> MainColor
                        "2" -> Blue40
                        "3" -> Purple40
                        else -> BackGround
                    }
                    Card(
                        colors = CardDefaults.cardColors(color),
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp),
                        onClick = {
                            editNum = i
                        }
                    ) {
                        println(i)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .align(Alignment.BottomEnd),
                                horizontalAlignment = Alignment.End,
                            ) {
                                IconButton(
                                    onClick = {
                                        if (InClick != 0) {

                                        } else {
                                            InClick++
                                            println(InClick)
                                            coroutineScope.launch {
                                                withContext(Dispatchers.IO) {
                                                    delay(400)
                                                    if (InClick == 1) {
                                                        if (arrString[1][i] == pickedDate.toString()) daily--
                                                        for (j in i until n) {
                                                            arrString[1][j] = arrString[1][j + 1]
                                                            arrString[2][j] = arrString[2][j + 1]
                                                            arrString[3][j] = arrString[3][j + 1]
                                                        }
                                                        arrString[0][n] = ""
                                                        arrString[1][n] = ""
                                                        arrString[2][n] = ""
                                                        arrString[3][n] = ""
                                                        saveArrayToSharedPreferences(context, "data")
                                                        n--
                                                        delay(10)
                                                        re = !re
                                                        re = !re
                                                        delay(10)
                                                    }
                                                    InClick = 0
                                                }
                                            }
                                        }
                                    },
                                    Modifier
                                        .padding(end = 8.dp, bottom = 13.dp)
                                        .fillMaxHeight()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp),
                                        tint = Color.White
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
                                    Text(text = arrString[2][i], fontSize = 18.sp, color = Color.White)
                                }
                                Text(
                                    text = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                                        .format(LocalDate.parse(arrString[1][i])),
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
        if (n == 0) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 90.dp)
            ) {
                Text(text = "请添加计划", color = Color.DarkGray)
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DailyNoteItem(
    callEdit: (Int?) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var re by remember {
        mutableStateOf(true)
    }
    val i = (0 until n).toList()
    if (re) {
        LazyColumn(modifier = Modifier) {
            item { Spacer(modifier = Modifier.height(10.dp)) }
            items(i) { i ->
                if (arrString[1][i] == pickedDate.toString()) {
                    val cardColor = when (arrString[3][i]) {
                        "1" -> MainColor
                        "2" -> Blue40
                        "3" -> Purple40
                        else -> Color.Transparent
                    }
                    Card(
                        colors = CardDefaults.cardColors(cardColor),
                        modifier = Modifier
                            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp)
                            .clickable {
                                callEdit(i)
                            }
                    ) {
                        println(i)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .align(Alignment.BottomEnd),
                                horizontalAlignment = Alignment.End,
                            ) {
                                IconButton(onClick = {
                                    if (InClick != 0) {

                                    } else {
                                        InClick++
                                        println(InClick)
                                        coroutineScope.launch {
                                            withContext(Dispatchers.IO) {
                                                delay(400)
                                                if (InClick == 1) {
                                                    for (j in i until n) {
                                                        arrString[1][j] = arrString[1][j + 1]
                                                        arrString[2][j] = arrString[2][j + 1]
                                                        arrString[3][j] = arrString[3][j + 1]
                                                    }
                                                    arrString[0][n] = ""
                                                    arrString[1][n] = ""
                                                    arrString[2][n] = ""
                                                    arrString[3][n] = ""
                                                    saveArrayToSharedPreferences(context, "data")
                                                    daily--
                                                    n--
                                                    delay(10)
                                                    re = !re
                                                    re = !re
                                                    delay(10)
                                                }
                                                InClick = 0
                                            }
                                        }
                                    }
                                }, Modifier.padding(bottom = 13.dp, end = 8.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        Modifier.size(25.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                            Row(
                                Modifier.padding(
                                    top = 25.dp,
                                    start = 25.dp,
                                    end = 50.dp,
                                    bottom = 25.dp
                                )
                            ) {
                                Text(text = arrString[2][i], fontSize = 18.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(95.dp)) }
        }
        if (daily == 0) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 185.dp)
            ) {
                Text(text = "今日暂无计划", color = Color.DarkGray)
            }
        }
    }
}
