package com.example.keeptodo

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.keeptodo.ui.theme.BackGround
import com.example.keeptodo.ui.theme.Color1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllNoteScreen(navController: NavController) {
    Surface(color = BackGround, modifier = Modifier.fillMaxSize(), shadowElevation = 50.dp) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection), containerColor = BackGround, contentColor = BackGround, topBar = {
            MediumTopAppBar(title = { Text(text = "KeepTODO所有計畫",Modifier.padding(10.dp)) }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }, modifier = Modifier.padding(start = 10.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(30.dp))
                }
            }, scrollBehavior = scrollBehavior, colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = BackGround, scrolledContainerColor = Color1),)
        }) { values ->
            Column(
                Modifier
                    .padding(values)
            )
            {
                NoteItem(navController)
            }
        }
    }
}
