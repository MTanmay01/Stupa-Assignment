package com.mtanmay.stupa.ui.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailId: String,
    onValueChange: (String) -> Unit,
    label: String = "Email ID",
    isError: Boolean
) {
    TextField(
        modifier = modifier,
        value = emailId,
        onValueChange = onValueChange,
        isError = isError,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions {
            defaultKeyboardAction(ImeAction.Next)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    onValueChange: (String) -> Unit,
    label: String = "Password",
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = password,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
            )
        },
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ) {
            defaultKeyboardAction(imeAction)
        },
        singleLine = true
    )
}