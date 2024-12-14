package com.bambang.simplecalculator.ui

sealed class CalculatorOperation(val symbol: String) {
    object Add: CalculatorOperation("+")
    object Subtract: CalculatorOperation("-")
    object Multiply: CalculatorOperation("\u00D7")
    object Divide: CalculatorOperation("\u00F7")
}