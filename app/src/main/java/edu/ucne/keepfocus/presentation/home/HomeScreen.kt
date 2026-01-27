package edu.ucne.keepfocus.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.keepfocus.R
import edu.ucne.keepfocus.presentation.component.TopAppBarComponent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    HomeBodyScreen(
        uiState = uiState
    )
}

@Composable
private fun HomeBodyScreen(
    uiState: HomeUiState
){
    Scaffold(
        topBar = { TopAppBarComponent() }
    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            when{
                uiState.isEmpty -> EmptyFocusZoneView()
                else -> FocusZoneList()
            }
        }
    }
}

@Composable
private fun EmptyFocusZoneView(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.cajavacia),
            contentDescription = "Empty List",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Aún no tienes zonas de enfoque, crea una para empezar a controlar tu tiempo en pantalla.",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(300.dp)
        )
    }
}

@Composable
private fun FocusZoneList(

){

}