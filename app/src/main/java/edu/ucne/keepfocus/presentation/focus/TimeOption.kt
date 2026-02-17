package edu.ucne.keepfocus.presentation.focus

data class TimeOption(
    val value: Long,
    val labelRes: String
)

fun getTimeOptions(): List<TimeOption>{
    return listOf(
        TimeOption(value = 1 * 60_000L, "1 Minuto"),
        TimeOption(value = 2 * 60_000L, "2 Minutos"),
        TimeOption(value = 3 * 60_000L, "3 Minutos"),
        TimeOption(value = 4 * 60_000L, "4 Minutos"),
        TimeOption(value = 5 * 60_000L, "5 Minutos"),
        TimeOption(value = 10 * 60_000L, "10 Minutos"),
        TimeOption(value = 15 * 60_000L, "15 Minutos"),
        TimeOption(value = 20 * 60_000L, "20 Minutos"),
        TimeOption(value = 30 * 60_000L, "30 Minutos"),
        TimeOption(value = 45 * 60_000L, "45 Minutos"),
        TimeOption(value = 60 * 60_000L, "1 Hora"),
        TimeOption(value = 120 * 60_000L, "2 Horas"),
        TimeOption(value = 180 * 60_000L, "3 Horas"),
    )
}