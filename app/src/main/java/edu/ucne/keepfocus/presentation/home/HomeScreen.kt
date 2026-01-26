package edu.ucne.keepfocus.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.ucne.keepfocus.presentation.component.TopAppBarComponent

@Composable
fun HomeScreen(

){
    HomeBodyScreen()
}

@Composable
private fun HomeBodyScreen(

){
    Scaffold(
        topBar = { TopAppBarComponent() }
    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            Text("HelloWorld")
        }
    }
}