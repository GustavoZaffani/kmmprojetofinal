package com.jetbrains.spacetutorial.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LabelCard(
    label: String,
    value: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value
        )
    }
}