package edu.ucne.keepfocus.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.ucne.keepfocus.R
import edu.ucne.keepfocus.domain.models.FocusZone
import edu.ucne.keepfocus.domain.models.FocusZoneWithApps
import edu.ucne.keepfocus.presentation.component.TopAppBarComponent
import edu.ucne.keepfocus.presentation.focus.AppUi
import edu.ucne.keepfocus.presentation.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    val message = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>("snackbar_message")

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<String>("snackbar_message")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is HomeUiEffect.NavigateToEdit -> {
                    navController.navigate(Screen.FocusScreen(effect.focusId))
                }
            }
        }
    }


    HomeBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onGetIcon = viewModel::getIcon,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun HomeBodyScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onGetIcon: (String) -> ImageBitmap?,
    snackbarHostState: SnackbarHostState
){
    Scaffold(
        topBar = { TopAppBarComponent() },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ){ innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            when{
                uiState.isEmpty -> EmptyFocusZoneView()
                else -> FocusZoneList(
                    onEvent = onEvent,
                    onGetIcon = onGetIcon,
                    focusZonesWithApps = uiState.focusZonesWithApps
                )
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
    onEvent: (HomeUiEvent) -> Unit,
    onGetIcon: (String) -> ImageBitmap?,
    focusZonesWithApps: List<FocusZoneWithApps>
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
    ){
        items(focusZonesWithApps){ focusZoneWithApps ->
            FocusZoneExpandableCard(
                onEvent = onEvent,
                focusZone = focusZoneWithApps.focusZone,
                apps = focusZoneWithApps.apps.map {
                    AppUi(
                        packageName = it.packageName,
                        name = it.name,
                        icon = onGetIcon.invoke(it.packageName)
                    )
                }
            )
        }
    }
}

@Composable
private fun FocusZoneExpandableCard(
    onEvent: (HomeUiEvent) -> Unit,
    focusZone: FocusZone,
    apps: List<AppUi>
){
    var expanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column {
            // 🔵 HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                // 🔹 BLOQUE IZQUIERDO (icono + textos)
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { expanded = !expanded },
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(id = focusZone.icono),
                        contentDescription = "Icono del Focus Zone",
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column{
                        Text(
                            text = "Focus: ${focusZone.nombre}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            letterSpacing = 0.3.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Límite de uso: ${getTimeUnit((focusZone.tiempoLimite / 60_000L).toInt())} diarios",
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f)
                        )
                    }
                }

                // 🔹 FLECHA (siempre visible)
                Icon(
                    imageVector = if (expanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                // 🔹 MENÚ (solo cuando está colapsado)
                if(!expanded){
                    Spacer(modifier = Modifier.width(8.dp))
                    MoreMenu(
                        focusId = focusZone.focusZoneId ?: 0,
                        onEvent = onEvent
                    )
                }
            }
        }

        // 🟢 APPS EXPANDIBLES
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 12.dp)
            ){
                apps.forEachIndexed { index, app ->
                    AppCardInside(app)
                    if (index != apps.lastIndex) {
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
}

@Composable
private fun AppCardInside(app: AppUi){
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
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)){
            Text(
                text = app.name,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { 0.90f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = Color(0xFF2FA69A), // verde progreso
                trackColor = Color(0xFFE6E0F0) // gris/morado claro
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "75%",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun MoreMenu(
    focusId: Int,
    onEvent: (HomeUiEvent) -> Unit
){
    var menuExpanded by remember { mutableStateOf(false) }

    Box{
        IconButton(onClick = { menuExpanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Opciones",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false}
        ) {
            DropdownMenuItem(
                text = { Text("Editar")},
                onClick = {
                    menuExpanded = false
                    onEvent(HomeUiEvent.OnEditFocus(focusId))
                }
            )
            DropdownMenuItem(
                text = { Text("Eliminar")},
                onClick = {
                    menuExpanded = false
                    onEvent(HomeUiEvent.OnDeleteFocus(focusId))
                }
            )
        }
    }
}

private fun getTimeUnit(value: Int): String{
    return when(value){
        1 -> "1 Minuto"
        2 -> "2 Minutos"
        3 -> "3 Minutos"
        4 -> "4 Minutos"
        5 -> "5 Minutos"
        10 -> "10 Minutos"
        15 -> "15 Minutos"
        20 -> "20 Minutos"
        30 -> "30 Minutos"
        45 -> "45 Minutos"
        60 -> "1 Hora"
        120 -> "2 Horas"
        180 -> "3 Horas"
        else -> "N/A"
    }
}