package com.jetbrains.spacetutorial.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TitleCard(value: String) {

    Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall
    )
}