package edu.ucne.keepfocus.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import edu.ucne.keepfocus.ui.theme.FocusAlert
import edu.ucne.keepfocus.ui.theme.FocusSuccess

@Composable
fun getColorByProgressBarPercent(time: Long): Color {
    return when(time){
        in 0..30 -> FocusSuccess
        in 31..60 -> Color(0xFFFFA500)
        else -> FocusAlert
    }
}

fun getTimeUnit(value: Int): String{
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