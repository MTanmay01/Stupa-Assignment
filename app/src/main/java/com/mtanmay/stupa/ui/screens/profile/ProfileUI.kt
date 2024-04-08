package com.mtanmay.stupa.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtanmay.domain.models.LocalUser
import com.mtanmay.stupa.ui.theme.primary

@Composable
fun ProfileUI(
    isLoading: Boolean = false,
    user: LocalUser? = null,
    onLogOut: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(primary)
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "USER PROFILE",
            style = MaterialTheme.typography.titleLarge
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
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp
                    )
                }
            }
            else if (user != null) {
                UserInfoField("Username", user.username)
                Spacer(modifier = Modifier.height(25.dp))
                UserInfoField("Email ID", user.emailId)
                Spacer(modifier = Modifier.height(25.dp))
                UserInfoField("Country", user.country)
                Spacer(modifier = Modifier.height(25.dp))
                UserInfoField("Phone number", user.phoneNumber)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    onClick = { onLogOut() }
                ) {
                    Text(
                        text = "LOG OUT",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
            else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Failed to fetch user profile",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoField(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            fontSize = 15.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileUIPreview() {
    ProfileUI(
        user = LocalUser(
            "Anonymous",
            "someone@mail.com",
            "123456789",
            "India"
        )
    )
}