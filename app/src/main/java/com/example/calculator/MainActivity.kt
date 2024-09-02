package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class Operator {
    PLUS,
    MINUS,
    MULTI,
    DIVISION,
    SQUARE,
    NONE
}

@Composable
fun RowScope.CalculatorButton(text: String, onClick: () -> Unit ) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxSize().aspectRatio(1f).padding(5.dp).weight(1f),
        shape = RoundedCornerShape(10.dp)

    ) {
        Text(text = text, style = TextStyle(
            fontSize = 50.sp
        ))
    }

}


fun calculate(firstNumber: Double, secondNumber: Double, operator: Operator) : Double? {
    var result : Double? = null

    when (operator) {
        Operator.PLUS -> result = firstNumber + secondNumber
        Operator.MINUS -> result = firstNumber - secondNumber
        Operator.MULTI -> result = firstNumber * secondNumber
        Operator.DIVISION -> result = firstNumber / secondNumber
        Operator.SQUARE -> TODO()
        Operator.NONE -> TODO()
    }

    return result
}

fun onClick(operator: MutableState<Operator>, firstNumber: MutableState<Double?>, secondNumber: MutableState<Double?>, text: MutableState<String>, resultLabel: MutableState<String>, textOperator: MutableState<String> ) : Unit {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text = remember { mutableStateOf("") }
    var resultLabel = remember { mutableStateOf("0.0") }
    var operator = remember { mutableStateOf<Operator>(Operator.NONE) }
    var textOperator = remember { mutableStateOf("") }
    var firstNumber = remember { mutableStateOf<Double?>(0.0) }
    var secondNumber = remember { mutableStateOf<Double?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.background(color = Color(25, 25, 25)).fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(3f).padding(5.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = resultLabel.value + textOperator.value,
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                lineHeight = 40.sp,
                fontSize = 40.sp,
                textAlign = TextAlign.Right,
            )
            Text(
                text = text.value,
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                color = Color(150, 150, 150),
                fontSize = 40.sp,
                lineHeight = 40.sp,
                textAlign = TextAlign.Right
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 5.dp),
        ) {
            CalculatorButton("C") {
                text.value = ""
                resultLabel.value = "0.0"
                textOperator.value = ""
                operator.value = Operator.NONE
                firstNumber.value = 0.0
                secondNumber.value = null
            }
            CalculatorButton("B") {
                text.value = text.value.dropLast(1)
                if (text.value == "") {
                    secondNumber.value = null
                } else {
                    secondNumber.value = text.value.toDouble()
                }
            }
            CalculatorButton("√") {
                if (text.value != "") {
                    firstNumber.value = sqrt(text.value.toDouble())
                    resultLabel.value = firstNumber.value.toString()
                    operator.value = Operator.SQUARE
                }
            }
            CalculatorButton("÷") {
                if (operator.value == Operator.NONE || operator.value == Operator.SQUARE) {
                    try {
                        if (operator.value != Operator.SQUARE) {
                            firstNumber.value = text.value.toDouble()
                            resultLabel.value = text.value
                            text.value = ""
                        } else {
                            text.value = ""
                            operator.value = Operator.DIVISION
                        }
                    } catch (e: NumberFormatException) { }
                }
                if (text.value != ""){
                    secondNumber.value = text.value.toDouble()
                }

                if (secondNumber.value != null) {
                    resultLabel.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value).toString()
                    firstNumber.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value)
                    secondNumber.value = null
                    text.value = ""
                }
                operator.value = Operator.DIVISION
                textOperator.value = " ÷"
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 5.dp),
        ) {
            CalculatorButton("7",) { text.value += "7" }
            CalculatorButton("8",) { text.value += "8" }
            CalculatorButton("9",) { text.value += "9" }
            CalculatorButton("×",) {
                if (operator.value == Operator.NONE || operator.value == Operator.SQUARE) {
                    try {
                        if (operator.value != Operator.SQUARE) {
                            firstNumber.value = text.value.toDouble()
                            resultLabel.value = text.value
                            text.value = ""
                        } else {
                            operator.value = Operator.MULTI
                            text.value = ""
                        }
                    } catch (e: NumberFormatException) { }
                }
                if (text.value != ""){
                    secondNumber.value = text.value.toDouble()
                }

                if (secondNumber.value != null) {
                    resultLabel.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value).toString()
                    firstNumber.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value)
                    secondNumber.value = null
                    text.value = ""
                }
                operator.value = Operator.MULTI
                textOperator.value = " ×"
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 5.dp),
        ) {
            CalculatorButton("4",) { text.value += "4" }
            CalculatorButton("5",) { text.value += "5" }
            CalculatorButton("6",) { text.value += "6" }
            CalculatorButton("-",) {
                if (operator.value == Operator.NONE || operator.value == Operator.SQUARE) {
                    try {
                        if (operator.value != Operator.SQUARE) {
                            firstNumber.value = text.value.toDouble()
                            resultLabel.value = text.value
                            text.value = ""
                        } else {
                            text.value = ""
                            operator.value = Operator.MINUS
                        }
                    } catch (e: NumberFormatException) { }
                }
                if (text.value != ""){
                    secondNumber.value = text.value.toDouble()
                }

                if (secondNumber.value != null) {
                    resultLabel.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value).toString()
                    firstNumber.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value)
                    secondNumber.value = null
                    text.value = ""
                }
                operator.value = Operator.MINUS
                textOperator.value = " -"
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 5.dp),
        ) {
            CalculatorButton("3",) { text.value += "3" }
            CalculatorButton("2",) { text.value += "2" }
            CalculatorButton("1",) { text.value += "1" }
            CalculatorButton("+",) {
                if (operator.value == Operator.NONE || operator.value == Operator.SQUARE) {
                    try {
                        if (operator.value != Operator.SQUARE) {
                            firstNumber.value = text.value.toDouble()
                            resultLabel.value = text.value
                            text.value = ""
                        } else {
                            text.value = ""
                            operator.value = Operator.PLUS
                        }
                    } catch (e: NumberFormatException) { }
                }
                if (text.value != ""){
                    secondNumber.value = text.value.toDouble()
                }

                if (secondNumber.value != null) {
                    resultLabel.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value).toString()
                    firstNumber.value = calculate(firstNumber.value!!, secondNumber.value!!, operator.value)
                    secondNumber.value = null
                    text.value = ""
                }
                operator.value = Operator.PLUS
                textOperator.value = " +"
            }
        }
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 5.dp),
            ) {
                CalculatorButton("∓",) {
                    if (text.value != "") {
                        text.value = (text.value.toDouble() * -1).toString()
                    }
                }

                CalculatorButton("0",) { text.value += "0" }
                CalculatorButton(".",) {
                    if (text.value == "") {
                        text.value += "0."
                    }
                    if (!text.value.toString().contains('.')) {
                        text.value += "."
                    }
                }
                CalculatorButton("=",) {
                    if (operator.value == Operator.NONE || operator.value == Operator.SQUARE) {
                        try {
                            if (operator.value != Operator.SQUARE) {
                                firstNumber.value = text.value.toDouble()
                                resultLabel.value = text.value
                                text.value = ""
                            } else {
                                resultLabel.value = firstNumber.value.toString()
                            }
                        } catch (e: NumberFormatException) {
                        }
                    } else {
                        if (secondNumber.value == null) {
                            secondNumber.value = firstNumber.value
                        }
                        if (text.value != "") {
                            secondNumber.value = text.value.toDouble()
                        }
                        resultLabel.value = calculate(
                            firstNumber.value!!,
                            secondNumber.value!!,
                            operator.value
                        ).toString()
                        firstNumber.value =
                            calculate(firstNumber.value!!, secondNumber.value!!, operator.value)
                        secondNumber.value = null
                    }
                }
            }
        }
    }


