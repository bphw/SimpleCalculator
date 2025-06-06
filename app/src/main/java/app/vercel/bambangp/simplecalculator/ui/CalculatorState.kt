package app.vercel.bambangp.simplecalculator.ui

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,
    val history: List<String> = emptyList(),
    val lastWasResult: Boolean = false
) {
}