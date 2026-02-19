@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package edu.ucne.keepfocus.presentation.focus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.keepfocus.R
import edu.ucne.keepfocus.presentation.component.TopAppBarComponent
import edu.ucne.keepfocus.ui.theme.FocusPrimary

@Composable
fun FocusScreen(
    viewModel: FocusViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    FocusBodyScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun FocusBodyScreen(
    uiState: FocusUiState,
    onEvent: (FocusUiEvent) -> Unit
){
    val apps = uiState.installedApps

    Scaffold(
        topBar = { TopAppBarComponent(title = "Registrar un Nuevo Focus", subtitle = "") }
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            item{
                Header(
                    uiState = uiState,
                    onEvent = { onEvent(FocusUiEvent.OnShowOverlay(FocusOverlay.IconPicker)) }
                )
            }
            item {
                AssistedTextField(
                    value = uiState.nombre,
                    onValueChange = { onEvent(FocusUiEvent.OnNombreChange(it)) },
                    onButtonClick = { onEvent(FocusUiEvent.OnShowOverlay(FocusOverlay.HelpFocusName)) }
                )
            }
            item {
                AssistedDropDown(
                    value = uiState.tiempoLimite,
                    onValueChange = { onEvent(FocusUiEvent.OnTiempoLimiteChange(it)) },
                    onButtonClick = { onEvent(FocusUiEvent.OnShowOverlay(FocusOverlay.HelpTimeLimit)) }
                )
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item{
                AppButton { onEvent(FocusUiEvent.OnShowOverlay(FocusOverlay.AppPicker)) }
            }

            if(uiState.isEmpty){
                item { EmptyAppsList() }
            } else{
                item {
                    SelectedAppsList(
                        apps = uiState.selectedApps,
                        onEvent = onEvent
                    )
                }
            }
        }

        when(uiState.overlay){
            FocusOverlay.AppPicker -> {
                AppModalPicker(
                    apps = apps,
                    onEvent = onEvent,
                    onDismiss = { onEvent(FocusUiEvent.OnDismissOverlay) }
                )
            }
            FocusOverlay.HelpFocusName -> {
                HelpModal(
                    title = "¿Qué es un Focus?",
                    description = "Un Focus es un grupo de aplicaciones al que le asignas un tiempo máximo de uso diario.",
                    onDismiss = { onEvent(FocusUiEvent.OnDismissOverlay)}
                )
            }
            FocusOverlay.HelpTimeLimit -> {
                HelpModal(
                    title = "¿Qué pasa con el tiempo?",
                    description = "Cuando el tiempo se agota, KeepFocus te avisa para ayudarte a volver al enfoque.",
                    onDismiss = { onEvent(FocusUiEvent.OnDismissOverlay)}
                )
            }
            FocusOverlay.IconPicker -> {
                IconPickerModal(
                    selectedIcono = uiState.icono,
                    onEvent = {
                        onEvent(FocusUiEvent.OnIconoChange(it))
                    },
                    onDismiss = { onEvent(FocusUiEvent.OnDismissOverlay)}
                )
            }
            FocusOverlay.None -> Unit
        }
    }
}

@Composable
private fun AppButton(onEvent: () -> Unit){
    Button(
        onClick = onEvent,
        border = BorderStroke(2.dp, FocusPrimary),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 60.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = FocusPrimary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = "Elige tus apps",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun Header(
    uiState: FocusUiState,
    onEvent: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = uiState.icono),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(0.75f)
            )
        }
        Spacer(modifier = Modifier.weight(2f))
        Button(
            onClick = onEvent,
            modifier = Modifier.height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Elige un icono",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun EmptyAppsList(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.cajavacia),
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Aún no has seleccinado aplicaciones para este focus.",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(300.dp)
        )
    }
}

@Composable
private fun SelectedAppsList(
    apps: List<AppUi>,
    onEvent: (FocusUiEvent) -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Aplicaciones Seleccionadas:",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 10.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp)
        ){
            LazyColumn(
                modifier = Modifier.heightIn(max = 200.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(
                    items = apps,
                    key = { it.packageName }
                ){ app ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        app.icon?.let {
                            Image(
                                bitmap = it,
                                contentDescription = app.name,
                                modifier = Modifier.size(45.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = app.name,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { onEvent(FocusUiEvent.OnDeleteSelectedApp(app.packageName))}
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar app de la lista",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 40.dp, vertical = 2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Guardar",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun HelpButton(
    onButtonClick: () -> Unit
){
    Spacer(modifier = Modifier.width(8.dp))
    IconButton(
        onClick = onButtonClick,
        modifier = Modifier
            .size(48.dp)
            .offset(y = 4.dp)
            .clip(CircleShape),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = Icons.Filled.QuestionMark,
            contentDescription = "Pregunta",
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
private fun AssistedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        OutlinedTextField(
            label = { Text("Nombre") },
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            maxLines = 1
        )

        HelpButton(onButtonClick = onButtonClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistedDropDown(
    value: Long,
    onValueChange: (Long) -> Unit,
    onButtonClick: () -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    val options = getTimeOptions()
    val minutes = value / 60_000L

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded},
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                label = { Text("Tiempo") },
                value = when{
                    minutes == 1L -> "1 Minuto"
                    minutes < 60L -> "$minutes Minutos"
                    minutes == 60L -> "1 Hora"
                    else -> "${minutes / 60} Horas"
                },
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .menuAnchor(),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults
                        .TrailingIcon(expanded = expanded)
                },
                readOnly = true,
                singleLine = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}
            ){
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.labelRes) },
                        onClick = {
                            onValueChange(option.value)
                            expanded = false
                        }
                    )
                }
            }
        }

        HelpButton(onButtonClick = onButtonClick)
    }
}

@Composable
private fun AppModalPicker(
    apps: List<AppUi>,
    onEvent: (FocusUiEvent) -> Unit,
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Aplicaciones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Selecciona las aplicaciones que deseas agregar a la lista.",
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(80.dp),
                    modifier = Modifier.height(300.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    items(apps){ app ->
                        OptionItem(
                            app = app,
                            selected = app.isSelected,
                            onSelected = { onEvent(FocusUiEvent.OnSelectedApp(app.packageName)) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onEvent(FocusUiEvent.OnAddSelectedApps(
                        apps.filter { it.isSelected }
                    )) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .padding(horizontal = 10.dp, vertical = 3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Continuar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun OptionItem(
    app: AppUi,
    selected: Boolean,
    onSelected: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected() }
    ){
        Text(
            text = app.name,
            fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp,
            color =  MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        app.icon?.let {
            Image(
                bitmap = it,
                contentDescription = app.name,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        SmallRadioButton(
            selected = selected,
            onButtonClick = onSelected
        )
    }
}

@Composable
private fun SmallRadioButton(
    selected: Boolean,
    onButtonClick: () -> Unit
){
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable { onButtonClick() },
        contentAlignment = Alignment.Center
    ){
        if(selected){
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}