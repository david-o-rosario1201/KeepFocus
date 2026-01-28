package edu.ucne.keepfocus.presentation.focus

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.keepfocus.presentation.component.TopAppBarComponent

@Composable
fun FocusScreen(

){
    FocusBodyScreen()
}

@Composable
private fun FocusBodyScreen(

){
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
                onClick = {},
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