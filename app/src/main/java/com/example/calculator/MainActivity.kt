package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var numbers = ArrayList<Int>()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize().background(color = Color.Yellow)
    ) {
        Row {
            OutlinedTextField(
                value = text,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    text = it
                },
                maxLines = 1
            )
        }
        Row (
            modifier = Modifier.background(Color.Cyan).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    text = text + "7"
                }
            ) {
                Text("7")
            }
            Button(
                onClick = {
                    text = text + "8"
                }
            ) {
                Text("8")
            }
            Button(
                onClick = {
                    text = text + "9"
                }
            ) {
                Text("9")
            }
            Button(
                onClick = {
                    numbers.add(text.toInt())
                    text = ""
                }
            ) {
                Text("*")
            }
        }
        Row (
            modifier = Modifier.background(Color.Cyan).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    text = text + "4"
                }
            ) {
                Text("4")
            }
            Button(onClick = {
                text = text + "5"
            }) {
                Text("5")
            }
            Button(onClick = {
                text = text + "6"
            }) {
                Text("6")
            }
            Button(onClick = {
                numbers.add(text.toInt())
                text = ""
            }) {
                Text("-")
            }
        }
        Row (
            modifier = Modifier.background(Color.Cyan).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = {
                text = text + "1"
            }) {
                Text("1")
            }
            Button(onClick = {
                text = text + "2"
            }) {
                Text("2")
            }
            Button(onClick = {
                text = text + "3"
            }) {
                Text("3")
            }
            Button(onClick = {
                numbers.add(text.toInt())
                text = ""
            }) {
                Text("+")
            }
        }
        Row (
            modifier = Modifier.background(Color.Cyan).fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = {
                text = ""
            },
                    ) {
                Text("+/-")
            }
            Button(onClick = {
                text = text + "0"
            },
                ) {
                Text("0")
            }
            Button(onClick = {
                text = ""
            },
                ) {
                Text("C")
            }
            Button(onClick = {
                text = text + "Пока не реализовал"
            },
                ) {
                Text("=")
            }
        }
    }
}

