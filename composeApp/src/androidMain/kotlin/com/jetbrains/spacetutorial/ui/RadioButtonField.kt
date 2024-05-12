package com.jetbrains.spacetutorial.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonField(
    radioValue: String,
    selectedOption: String,
    radioLabel: String,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    RadioButton(
        enabled = isEnabled,
        selected = selectedOption == radioValue,
        onClick = { onValueChange(radioValue) },
        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
    )
    Text(
        text = radioLabel,
        modifier = Modifier.padding(start = 2.dp)
    )
}