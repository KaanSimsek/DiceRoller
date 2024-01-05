package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRoller()
        }
    }
}

@Composable
fun DiceRoller() {
    var results by remember { mutableStateOf(emptyList<Int>()) }
    val diceOptions = listOf("D4", "D6", "D8", "D10", "D12", "D20")
    val expanded = remember { mutableStateOf(false) }
    val selectedDice = remember { mutableStateOf(emptyList<String>()) }

    Column {
        // Dropdown menu for selecting dice
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            diceOptions.forEach { option ->
                DropdownMenuItem(
                    { Text(text = option) },
                    onClick = {
                    if (selectedDice.value.contains(option)) {
                        selectedDice.value = selectedDice.value - option
                    } else {
                        selectedDice.value = selectedDice.value + option
                    }
                })

            }
        }

        // Display selected dice
        Button(onClick = { expanded.value = true }) {
            Text(text = "D6")
        }

        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

        // Roll Button
        Button(onClick = {
            results = selectedDice.value.map { diceType ->
                val sides = diceType.substring(1).toInt()
                (1..sides).random()
            }
        }) {
            Text(text = "Roll!")
        }

        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

        // Display rolled results
        Column {
            results.forEach { result ->
                Text(text = "Result: $result")
            }
        }
    }
}