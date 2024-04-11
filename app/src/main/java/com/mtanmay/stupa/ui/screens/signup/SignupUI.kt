package com.mtanmay.stupa.ui.screens.signup

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtanmay.data.models.DBUser
import com.mtanmay.domain.models.interfaces.User
import com.mtanmay.stupa.ui.common.EmailTextField
import com.mtanmay.stupa.ui.common.PasswordTextField
import com.mtanmay.stupa.ui.theme.primaryDark
import com.mtanmay.stupa.utils.Validator

@Composable
fun SignupUI(
    modifier: Modifier = Modifier,
    onSignup: (User) -> Unit,
    signUpInProcess: Boolean = false,
    userAlreadyExists: Boolean = false,
    onSignIn: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    var emailId: String by remember { mutableStateOf("") }
    var emailIdError: Boolean by remember { mutableStateOf(false) }

    var password: String by remember { mutableStateOf("") }

    var passwordConfirm: String by remember { mutableStateOf("") }
    var passwordConfirmError: Boolean by remember { mutableStateOf(false) }

    var username: String by remember { mutableStateOf("") }
    var country: String by remember { mutableStateOf("") }
    var phoneNumber: String by remember { mutableStateOf("") }

    var isUserValid: Boolean by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(primaryDark)
    ) {
        Spacer(modifier = Modifier.height(45.dp))
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "SIGN UP",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "Create your account",
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
                .padding(25.dp)
        ) {
            EmailTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                emailId = emailId,
                onValueChange = {
                    emailIdError = false
                    emailId = it
                },
                isError = emailIdError
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        text = "Username",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                singleLine = true,
                keyboardActions = KeyboardActions {
                    defaultKeyboardAction(ImeAction.Next)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = country,
                onValueChange = { country = it },
                label = {
                    Text(
                        text = "Country",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                singleLine = true,
                keyboardActions = KeyboardActions {
                    defaultKeyboardAction(ImeAction.Next)
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = {
                    Text(
                        text = "Phone number",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                singleLine = true,
                keyboardActions = KeyboardActions {
                    defaultKeyboardAction(ImeAction.Next)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                password = password,
                onValueChange = { password = it },
                imeAction = ImeAction.Next
            )
            Spacer(modifier = Modifier.height(15.dp))
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                password = passwordConfirm,
                onValueChange = {
                    passwordConfirmError = false
                    passwordConfirm = it
                },
                label = "Re-enter your password",
                isError = passwordConfirmError
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = if (userAlreadyExists) "Email already registered!"
                else "",
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(15.dp))
            if (!isUserValid) {
                Text(
                    text = "Please fill all the fields",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                onClick = {
                    if (!Validator.isEmailValid(emailId))
                        emailIdError = true
                    else if (
                        !Validator.isPasswordValid(
                            password, passwordConfirm
                        )
                    )
                        passwordConfirmError = true
                    else {
                        focusManager.clearFocus()

                        val user = DBUser(
                            username = username,
                            emailId = emailId,
                            password = password,
                            phoneNumber = phoneNumber,
                            country = country
                        )
                        if (Validator.isUserValid(user)) {
                            isUserValid = true
                            onSignup(user)
                        } else isUserValid = false
                    }
                }
            ) {
                if (signUpInProcess) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(20.dp)
                    )
                }
                Text(
                    text = "SIGN UP",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSignIn() },
                textAlign = TextAlign.Center,
                text = "Already have an account? Sign In",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun SignupUIPreview() {
    SignupUI(
        onSignup = {}
    )
}