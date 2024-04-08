package com.mtanmay.stupa.ui.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtanmay.stupa.ui.common.EmailTextField
import com.mtanmay.stupa.ui.common.PasswordTextField
import com.mtanmay.stupa.ui.theme.primaryDark
import com.mtanmay.stupa.utils.Validator

@Composable
fun SignInUI(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onResetState: () -> Unit = {},
    isInvalidCredential: Boolean = false,
    onSignUp: () -> Unit = {},
    onSignIn: (String, String) -> Unit = { _, _ -> }
) {
    val focusManager = LocalFocusManager.current

    var emailId: String by remember { mutableStateOf("") }
    var emailError: Boolean by remember { mutableStateOf(false) }

    var password: String by remember { mutableStateOf("") }
    var passwordError: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(primaryDark)
    ) {
        Spacer(modifier = Modifier.height(45.dp))
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "SIGN IN",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "Please sign in to continue",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp
                    )
                )
                .background(Color.Black)
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                emailId = emailId,
                onValueChange = {
                    onResetState()
                    emailError = false
                    emailId = it
                },
                isError = emailError || isInvalidCredential
            )
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                password = password,
                onValueChange = {
                    onResetState()
                    passwordError = false
                    password = it
                },
                isError = passwordError || isInvalidCredential
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = if (isInvalidCredential) "Invalid Credentials"
                else "",
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(80.dp))
            Button(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                onClick = {
                    focusManager.clearFocus()
                    if (!Validator.isEmailValid(emailId))
                        emailError = true
                    else if (password.isBlank())
                        passwordError = true
                    else {
                        onResetState()
                        onSignIn(emailId.trim(), password.trim())
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(20.dp)
                    )
                }
                Text(
                    text = "SIGN IN",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSignUp() },
                textAlign = TextAlign.Center,
                text = "Don't have an account? Sign Up",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }

}

@Preview
@Composable
private fun SignInUIPreview() {
    SignInUI()
}