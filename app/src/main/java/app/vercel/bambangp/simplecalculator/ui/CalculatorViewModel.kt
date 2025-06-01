package app.vercel.bambangp.simplecalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set
    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Calculate -> performCalculation()
        }
    }
    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }
    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if(number1 != null && number2 != null) {
            val result = when(state.operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                null -> return
            }
            val getIntResult = { res: Double -> if (res.toString().endsWith(".0")) { res.toInt() } else { res } }
            val resultStr = getIntResult(result).toString().take(15)
            val opSymbol = state.operation?.symbol ?: ""
            val historyEntry = "${state.number1} $opSymbol ${state.number2} = $resultStr"
            val newHistory = (listOf(historyEntry) + state.history).take(5)
            state = state.copy(
                number1 = resultStr,
                number2 = "",
                operation = null,
                history = newHistory,
                lastWasResult = true
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            if (state.number2.isNotBlank() && state.operation != null) {
                // Perform chained calculation
                val number1 = state.number1.toDoubleOrNull()
                val number2 = state.number2.toDoubleOrNull()
                if (number1 != null && number2 != null) {
                    val result = when (state.operation) {
                        is CalculatorOperation.Add -> number1 + number2
                        is CalculatorOperation.Subtract -> number1 - number2
                        is CalculatorOperation.Multiply -> number1 * number2
                        is CalculatorOperation.Divide -> number1 / number2
                        null -> return
                    }
                    val getIntResult = { res: Double -> if (res.toString().endsWith(".0")) { res.toInt() } else { res } }
                    val resultStr = getIntResult(result).toString().take(15)
                    val opSymbol = operation.symbol
                    state = state.copy(
                        number1 = resultStr,
                        number2 = "",
                        operation = operation,
                        lastWasResult = false
                    )
                    return
                }
            }
            state = state.copy(operation = operation)
        }
    }
    private fun enterDecimal() {
        if (state.operation == null
            && !state.number1.contains(".")
            && state.number1.isNotBlank()
            ) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        else if (!state.number2.contains(".")
            && state.number2.isNotBlank()
        ) {
            state = state.copy(
                number2 = state.number2 + "."
            )
            return
        }
    }
    private fun enterNumber(number: Int) {
        if (state.lastWasResult) {
            // Save last calculation to history if not already present
            val opSymbol = state.operation?.symbol ?: ""
            val lastEntry = if (state.number1.isNotBlank() && opSymbol.isNotBlank() && state.number2.isNotBlank()) {
                "${state.number1} $opSymbol ${state.number2} = ${state.number1}"
            } else null
            val newHistory = lastEntry?.let {
                if (state.history.firstOrNull() != it) (listOf(it) + state.history).take(5) else state.history
            } ?: state.history
            // Start new calculation, clear LCD
            state = state.copy(
                number1 = number.toString(),
                number2 = "",
                operation = null,
                lastWasResult = false,
                history = newHistory
            )
            return
        }
        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        if (state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    fun loadHistoryResult(result: String) {
        // Load result into LCD for new calculation
        state = state.copy(
            number1 = result,
            number2 = "",
            operation = null,
            lastWasResult = false
        )
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}