@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package app.vercel.bambangp.simplecalculator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vercel.bambangp.simplecalculator.ui.theme.MediumGray
import app.vercel.bambangp.simplecalculator.ui.theme.PurpleGrey40
import app.vercel.bambangp.simplecalculator.ui.theme.Teal200
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription

@Composable
private fun CalculatorDisplay(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    onCopyResult: (String) -> Unit = {}
) {
    val displayText = state.number2.ifEmpty { state.number1 }
    val prevText = if (state.number1.isNotEmpty() && state.operation != null) state.number1 + state.operation.symbol else ""

    // Dynamically adjust font size based on length
    val fontSize = when {
        displayText.length <= 7 -> 80.sp
        displayText.length <= 10 -> 56.sp
        displayText.length <= 14 -> 40.sp
        else -> 32.sp
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.End
    ) {
        if (prevText.isNotEmpty()) {
            Text(
                text = prevText,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontWeight = FontWeight.Light,
                fontSize = 32.sp,
                color = Color.White.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Clip,
                softWrap = false
            )
        }
        Text(
            text = displayText,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = displayText.isNotBlank()) { onCopyResult(displayText) }
                .semantics { contentDescription = "Result LCD" },
            fontWeight = FontWeight.Light,
            fontSize = fontSize,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            softWrap = false
        )
    }
}

@Composable
fun Calculator(
    state: CalculatorState,
    buttonSpacing: Dp = 8.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onAction: (CalculatorAction) -> Unit,
    onLoadHistoryResult: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showHistory by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier) {
        // Top-right icons column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 8.dp)
                .align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = { showInfo = true }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "App Info",
                    tint = Color.White
                )
            }
            IconButton(onClick = { showHistory = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Show History",
                    tint = Color.White
                )
            }
        }
        // Main calculator content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            CalculatorDisplay(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                onCopyResult = { value ->
                    clipboardManager.setText(AnnotatedString(value))
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Copied!")
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(symbol = "CA",
                    modifier = Modifier
                        .background(PurpleGrey40)
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = {
                        onAction(CalculatorAction.Clear)
                    }
                )
                CalculatorButton(symbol = "\u232B",
                    modifier = Modifier
                        .background(Color.LightGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Delete)
                    }
                )
                CalculatorButton(symbol = "\u00F7",
                    modifier = Modifier
                        .background(Teal200)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
                    }
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(symbol = "7",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(7))
                    }
                )
                CalculatorButton(symbol = "8",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(8))
                    }
                )
                CalculatorButton(symbol = "9",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(9))
                    }
                )
                CalculatorButton(symbol = "\u2A2F",
                    modifier = Modifier
                        .background(Teal200)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(symbol = "4",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(4))
                    }
                )
                CalculatorButton(symbol = "5",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(5))
                    }
                )
                CalculatorButton(symbol = "6",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(6))
                    }
                )
                CalculatorButton(symbol = "-",
                    modifier = Modifier
                        .background(Teal200)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(symbol = "1",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(1))
                    }
                )
                CalculatorButton(symbol = "2",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(2))
                    }
                )
                CalculatorButton(symbol = "3",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Number(3))
                    }
                )
                CalculatorButton(symbol = "+",
                    modifier = Modifier
                        .background(Teal200)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Operation(CalculatorOperation.Add))
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(symbol = "0",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = {
                        onAction(CalculatorAction.Number(0))
                    }
                )
                CalculatorButton(symbol = ".",
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Decimal)
                    }
                )
                CalculatorButton(symbol = "=",
                    modifier = Modifier
                        .background(Teal200)
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = {
                        onAction(CalculatorAction.Calculate)
                    }
                )
            }
        }
        // Modal Bottom Sheet for History
        if (showHistory) {
            ModalBottomSheet(
                onDismissRequest = { showHistory = false },
                sheetState = sheetState,
                containerColor = MediumGray
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 40.dp)
                ) {
                    Text(
                        text = "Calculation History",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    if (state.history.isEmpty()) {
                        Text(
                            text = "No history yet.",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    } else {
                        LazyColumn {
                            items(state.history) { entry ->
                                val result = entry.substringAfterLast("=").trim()
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp)
                                        .background(Color.Transparent)
                                        .clickable {
                                            onLoadHistoryResult(result)
                                            coroutineScope.launch { sheetState.hide() }
                                            showHistory = false
                                        }
                                ) {
                                    Text(
                                        text = entry,
                                        fontSize = 18.sp,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        // Snackbar for copy feedback
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        // Info dialog
        if (showInfo) {
            AlertDialog(
                onDismissRequest = { showInfo = false },
                title = { Text("About") },
                text = { Text("Simple Calculator © 2025\nfrom bambangp") },
                confirmButton = {
                    Button(onClick = {
                        uriHandler.openUri("https://bambangp.vercel.app")
                        showInfo = false
                    }) {
                        Text("Visit Website")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showInfo = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}