package edu.ucne.keepfocus.presentation.focus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.keepfocus.R
import edu.ucne.keepfocus.presentation.component.TopAppBarComponent
import edu.ucne.keepfocus.presentation.component.getColorByProgressBarPercent

@Composable
fun FocusDetailScreen(
    goBack: () -> Unit,
    viewModel: FocusViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    FocusDetailBodyScreen(
        uiState = uiState,
        goBack = goBack
    )
}

@Composable
private fun FocusDetailBodyScreen(
    uiState: FocusUiState,
    goBack: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "Focus: ${uiState.nombre}",
                subtitle = "",
                navigationIcon = goBack
            )
        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.elevatedCardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
            ){
                Column(
                    modifier = Modifier.padding(20.dp)
                ){
                    TiempoLimite(uiState)
                    Spacer(modifier = Modifier.height(16.dp))
                    ProgressBar(uiState)
                    Spacer(modifier = Modifier.height(16.dp))
                    AppDetails(
                        uiState = uiState,
                        apps = uiState.selectedApps.map {
                            AppUi(
                                packageName = it.packageName,
                                name = it.name,
                                icon = it.icon
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TiempoLimite(uiState: FocusUiState){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Tiempo Limite Diario",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${uiState.timeSpent}/${uiState.tiempoLimite / 60_000L} min",
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ProgressBar(
    uiState: FocusUiState
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Progreso Total del Focus",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                LinearProgressIndicator(
                    progress = { uiState.progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(50)),
                    color = getColorByProgressBarPercent(uiState.percent),
                    trackColor = Color.LightGray.copy(alpha = 0.5f)
                )
                Text(
                    text = "${uiState.percent}%"
                )
            }
        }
    }
}

@Composable
private fun AppDetails(
    uiState: FocusUiState,
    apps: List<AppUi>
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Detalles por Aplicación",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            apps.forEachIndexed { index, appUi ->
                AppDetail(
                    app = appUi,
                    uiState = uiState
                )
                if(index != apps.lastIndex){
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AppDetail(
    app: AppUi,
    uiState: FocusUiState
){
    val appProgress = if(uiState.tiempoLimite > 0){
        app.timeSpent.toFloat() / uiState.tiempoLimite.toFloat()
    } else 0f

    val appPercent = if(uiState.tiempoLimite > 0){
        (app.timeSpent.toFloat() / uiState.tiempoLimite.toFloat() * 100).toLong()
    } else 0L

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        app.icon?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)){
            // 🔹 Nombre + porcentaje en la misma línea
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = app.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Text(
                    text = "$appPercent%",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Usado hoy: ${app.timeSpent} min",
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { appProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(50)),
                color = getColorByProgressBarPercent(appPercent),
                trackColor = Color.LightGray.copy(alpha = 0.5f)
            )
        }
    }
}