package com.example.keeptodo.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.keeptodo.room.ContactState
import com.example.keeptodo.room.HistoryContactState
import com.example.keeptodo.room.arrDate
import com.example.keeptodo.room.arrShowColor
import com.example.keeptodo.ui.theme.Blue40
import com.example.keeptodo.ui.theme.Green40
import com.example.keeptodo.ui.theme.GreenBorder
import com.example.keeptodo.ui.theme.Purple40
import java.time.DayOfWeek
import java.time.DayOfWeek.*
import java.time.LocalDate
import kotlin.Float.Companion.NaN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalenderScreen(navController: NavController, historyState: HistoryContactState) {
    var year by remember {
        mutableStateOf(LocalDate.now().year)
    }
    var month by remember {
        mutableStateOf(LocalDate.now().monthValue)
    }
    var showCalender by remember {
        mutableStateOf(true)
    }
    var monthValue by remember {
        mutableStateOf(0)
    }
    var dateValue by remember {
        mutableStateOf(0)
    }
    var maxValue by remember {
        mutableStateOf(0)
    }
    var greenValue by remember {
        mutableStateOf(0)
    }
    var blueValue by remember {
        mutableStateOf(0)
    }
    var purpleValue by remember {
        mutableStateOf(0)
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background, shadowElevation = 50.dp) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "${year}年${month}月",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }, modifier = Modifier.padding(end = 10.dp)) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background
                    ),
                )
            },
        ) { values ->
            LazyColumn(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .padding()
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Text(
                            text = "日",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "一",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "二",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "三",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "四",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "五",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "六",
                            fontSize = 12.sp,
                            modifier = Modifier.width(55.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (showCalender) {
                        writeToShowArr(
                            historyState,
                            year,
                            month,
                            monthValues = { monthValue = it },
                            dateValues = { dateValue = it },
                            greenValues = {greenValue = it},
                            blueValues = {blueValue = it},
                            purpleValues = {purpleValue = it})
                        writeToArr(
                            firstDateOfMonth(year, "%02d".format(month)),
                            year,
                            month,
                            maxValues = { maxValue = it })
                        for (i in 0..4) {
                            Column(Modifier.height(65.dp)) {
                                Row() {
                                    for (j in 0..6) {
                                        Column(Modifier.width(55.dp), Arrangement.Top, CenterHorizontally) {
                                            Text(text = arrDate[i][j], fontSize = 20.sp)
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Row() {
                                                if (arrDate[i][j] != "") {
                                                    if (arrShowColor[arrDate[i][j].toInt()][0] == 1) Card(
                                                        colors = CardDefaults.cardColors(
                                                            Green40
                                                        ), modifier = Modifier
                                                            .size(10.dp)
                                                            .padding(2.dp)
                                                    ) {}
                                                    if (arrShowColor[arrDate[i][j].toInt()][1] == 1) Card(
                                                        colors = CardDefaults.cardColors(
                                                            Blue40
                                                        ), modifier = Modifier
                                                            .size(10.dp)
                                                            .padding(2.dp)
                                                    ) {}
                                                    if (arrShowColor[arrDate[i][j].toInt()][2] == 1) Card(
                                                        colors = CardDefaults.cardColors(
                                                            Purple40
                                                        ), modifier = Modifier
                                                            .size(10.dp)
                                                            .padding(2.dp)
                                                    ) {}
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Box {
                        if (showCalender) {
                            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                for (j in 0..6) {
                                    println(arrDate[5][j])
                                    Column(Modifier.width(55.dp), Arrangement.Top, CenterHorizontally) {
                                        Text(text = arrDate[5][j], fontSize = 20.sp)
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Row() {
                                            if (arrDate[5][j] != "") {
                                                if (arrShowColor[arrDate[5][j].toInt()][0] == 1) Card(
                                                    colors = CardDefaults.cardColors(
                                                        Green40
                                                    ), modifier = Modifier
                                                        .size(10.dp)
                                                        .padding(2.dp)
                                                ) {}
                                                if (arrShowColor[arrDate[5][j].toInt()][1] == 1) Card(
                                                    colors = CardDefaults.cardColors(
                                                        Blue40
                                                    ), modifier = Modifier
                                                        .size(10.dp)
                                                        .padding(2.dp)
                                                ) {}
                                                if (arrShowColor[arrDate[5][j].toInt()][2] == 1) Card(
                                                    colors = CardDefaults.cardColors(
                                                        Purple40
                                                    ), modifier = Modifier
                                                        .size(10.dp)
                                                        .padding(2.dp)
                                                ) {}
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.End, modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        ) {
                            Card(
                                modifier = Modifier.clickable {
                                    if (month == 1) {
                                        year--
                                        month = 12
                                    } else month--
                                    showCalender = false
                                    showCalender = true
                                },
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                                border = BorderStroke(width = 1.dp, color = GreenBorder)
                            ) {
                                Icon(
                                    Icons.Default.ArrowLeft,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Card(
                                modifier = Modifier.clickable {
                                    if (month == 12) {
                                        year++
                                        month = 1
                                    } else month++
                                    showCalender = false
                                    showCalender = true
                                },
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                                border = BorderStroke(width = 1.dp, color = GreenBorder),
                            ) {
                                Icon(
                                    Icons.Default.ArrowRight,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Column(modifier = Modifier.width(375.dp)) {
                        Text(text = "本月数据", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "已完成计划${monthValue}次", fontSize = 18.sp)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            border = BorderStroke(1.dp, GreenBorder),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = monthValue.toFloat() / maxValue.toFloat())
                                    .fillMaxHeight(),
                                colors = CardDefaults.cardColors(Green40),
                                shape = RoundedCornerShape(0.dp)
                            ) {

                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "已坚持打卡${dateValue}天", fontSize = 18.sp)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            border = BorderStroke(1.dp, GreenBorder),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = dateValue.toFloat() / maxValue.toFloat())
                                    .fillMaxHeight(),
                                colors = CardDefaults.cardColors(Green40),
                                shape = RoundedCornerShape(0.dp)
                            ) {

                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "本月计划色卡", fontSize = 18.sp)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            border = BorderStroke(1.dp, GreenBorder),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                        ) {
                            Row {
                                if (!(greenValue.toFloat()/(greenValue+blueValue+purpleValue).toFloat()).isNaN()){
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = greenValue.toFloat() / (greenValue + blueValue + purpleValue).toFloat())
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(Green40),
                                    shape = RoundedCornerShape(0.dp)
                                ) {

                                }}
                                if (!(blueValue.toFloat()/(blueValue+purpleValue).toFloat()).isNaN()){
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = blueValue.toFloat() / (blueValue + purpleValue).toFloat())
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(Blue40),
                                    shape = RoundedCornerShape(0.dp)
                                ) {

                                }}
                                if (!(purpleValue.toFloat() / purpleValue.toFloat()).isNaN()){
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(fraction = purpleValue.toFloat() / purpleValue.toFloat())
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(Purple40),
                                    shape = RoundedCornerShape(0.dp)
                                ) {

                                } }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun firstDateOfMonth(year: Int, month: String): Int {
    return when (LocalDate.parse("$year-$month-01").dayOfWeek) {
        MONDAY -> 1
        TUESDAY -> 2
        WEDNESDAY -> 3
        THURSDAY -> 4
        FRIDAY -> 5
        SATURDAY -> 6
        SUNDAY -> 0
    }
}

fun writeToArr(startWeek: Int, year: Int, month: Int, maxValues: (Int) -> Unit) {
    var max = 0
    if (year % 4 == 0 && year % 100 != 0) {
        when (month) {
            1 -> max = 31
            2 -> max = 29
            3 -> max = 31
            4 -> max = 30
            5 -> max = 31
            6 -> max = 30
            7 -> max = 31
            8 -> max = 31
            9 -> max = 30
            10 -> max = 31
            11 -> max = 30
            12 -> max = 31
        }
    } else {
        when (month) {
            1 -> max = 31
            2 -> max = 28
            3 -> max = 31
            4 -> max = 30
            5 -> max = 31
            6 -> max = 30
            7 -> max = 31
            8 -> max = 31
            9 -> max = 30
            10 -> max = 31
            11 -> max = 30
            12 -> max = 31
        }
    }
    var n = 1
    for (i in 0..5) {
        for (j in 0..6) {
            arrDate[i][j] = ""
        }
    }
    for (j in 0..6) {
        if (j >= startWeek) {
            arrDate[0][j] = n.toString()
            n++
        }
    }
    for (i in 1..5) {
        for (j in 0..6) {
            if (n <= max) {
                arrDate[i][j] = n.toString()
                n++
            }
        }
    }
    maxValues(max)
}


fun writeToShowArr(
    historyState: HistoryContactState,
    year: Int,
    month: Int,
    monthValues: (Int) -> Unit,
    dateValues: (Int) -> Unit,
    greenValues:(Int) ->Unit,
    blueValues:(Int) ->Unit,
    purpleValues:(Int)->Unit
) {
    var value = 0
    var dateValue = 0
    var greenValue = 0
    var blueValue = 0
    var purpleValue = 0
    for (i in 1..31) {
        arrShowColor[i][0] = 0
        arrShowColor[i][1] = 0
        arrShowColor[i][2] = 0
    }
    for (contacts in historyState.contacts) {
        if (contacts.month == "${year}年${"%02d".format(month)}月") {
            value++
            for (i in 1..31) {
                if (contacts.date == "${year}年${"%02d".format(month)}月${"%02d".format(i)}日") {
                    if (contacts.color == 1) {
                        arrShowColor[i][0] = 1
                        greenValue++
                    }
                    if (contacts.color == 2) {
                        arrShowColor[i][1] = 1
                        blueValue++
                    }
                    if (contacts.color == 3) {
                        arrShowColor[i][2] = 1
                        purpleValue++
                    }
                }
            }
        }
    }
    for (i in 1..31) {
        if (arrShowColor[i][0] == 1 || arrShowColor[i][1] == 1 || arrShowColor[i][2] == 1) {
            dateValue++
        }
    }
    monthValues(value)
    dateValues(dateValue)
    greenValues(greenValue)
    blueValues(blueValue)
    purpleValues(purpleValue)
}
