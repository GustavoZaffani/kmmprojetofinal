package com.jetbrains.spacetutorial.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jetbrains.spacetutorial.constants.Constants.EMPTY
import com.jetbrains.spacetutorial.constants.TranslationConstants

@Composable
fun StringField(
    fieldValue: String,
    fieldLabel: String,
    isRequired: Boolean,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit
): String {

    var fieldError by remember {
        if (isRequired && fieldValue.isEmpty())
            mutableStateOf(TranslationConstants.CAMPO_OBRIGATORIO)
        else mutableStateOf(EMPTY)
    }

    TextField(
        enabled = isEnabled,
        value = fieldValue,
        onValueChange = {
            onValueChange(it)

            if (isRequired) {
                fieldError = if (it.isEmpty()) TranslationConstants.CAMPO_OBRIGATORIO else EMPTY
            }
        },
        label = { Text(text = fieldLabel) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        isError = fieldError.isNotEmpty()
    )

    Text(text = fieldError, color = Color.Red)

    return fieldError
}