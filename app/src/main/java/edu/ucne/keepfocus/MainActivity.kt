package edu.ucne.keepfocus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.keepfocus.presentation.navigation.KeepFocusNavHost
import edu.ucne.keepfocus.ui.theme.KeepFocusTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KeepFocusTheme(dynamicColor = false){
               KeepFocusNavHost()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KeepFocusTheme {
       KeepFocusNavHost()
    }
}