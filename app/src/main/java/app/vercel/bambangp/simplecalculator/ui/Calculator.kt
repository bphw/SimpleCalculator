package app.vercel.bambangp.simplecalculator.ui

import android.annotation.SuppressLint
import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.vercel.bambangp.simplecalculator.ui.theme.MediumGray
import app.vercel.bambangp.simplecalculator.ui.theme.PurpleGrey40
import app.vercel.bambangp.simplecalculator.ui.theme.Teal200

@Composable
fun Calculator(
    state: CalculatorState,
    buttonSpacing: Dp = 8.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onAction: (CalculatorAction) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    Box(modifier = modifier) {
       Column (
           modifier = Modifier
               .fillMaxWidth()
               .align(Alignment.Companion.BottomCenter),
           verticalArrangement = Arrangement.spacedBy(buttonSpacing)
       ) {
           Text(
               text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
               textAlign = TextAlign.End,
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(vertical = 32.dp),
               fontWeight = FontWeight.Light,
               fontSize = 80.sp,
               color = Color.White,
               maxLines = 2,
//               onPress = {
//                   clipboardManager.setText(AnnotatedString(text = state.number1 + (state.operation?.symbol ?: "") + state.number2))
//               }
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
    }
}