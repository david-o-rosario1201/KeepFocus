@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package edu.ucne.keepfocus.presentation.focus

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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            AssistedTextField(
                value = "",
                onValueChange = {},
                onButtonClick = {}
            )
            AssistedDropDown(
                onButtonClick = {}
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = { onEvent(FocusUiEvent.OnOpenModal) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 40.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Elige tus apps",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            if(uiState.showAppPickerModal){
                AppModalPicker(
                    apps = apps,
                    onEvent = onEvent,
                    onDismiss = { onEvent(FocusUiEvent.OnCloseModal) }
                )
            }

            if(uiState.isEmpty){
                EmptyAppsList()
            } else{
                SelectedAppsList(
                    apps = uiState.selectedApps,
                    onEvent = onEvent
                )
            }
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
                                contentDescription = "Eliminar app de la lista"
                            )
                        }
                    }
                }
            }
        }
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
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
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
                .clip(RoundedCornerShape(10.dp))
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }
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
    onButtonClick: () -> Unit
){
    var expanded by remember { mutableStateOf(false) }

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
                value = "",
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
                DropdownMenuItem(
                    text = { Text("30") },
                    onClick = { expanded = false}
                )
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