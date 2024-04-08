package com.mtanmay.stupa.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtanmay.stupa.R
import com.mtanmay.stupa.navigation.NavGraph
import com.mtanmay.stupa.navigation.RootScreen
import com.mtanmay.stupa.utils.PreferencesManager
import kotlinx.coroutines.delay

@Composable
fun SplashUI(
    modifier: Modifier = Modifier,
    onDismiss: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val preferenceManager = remember { PreferencesManager(context) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .defaultMinSize(200.dp, 122.dp),
            painter = painterResource(id = R.drawable.stupa_logo),
            contentDescription = null
        )
    }
    LaunchedEffect(Unit) {
        delay(500)
        if (preferenceManager.isUserLoggedIn())
            onDismiss(RootScreen.HOME.route)
        else
            onDismiss(NavGraph.AUTHENTICATION)
    }
}

@Preview
@Composable
private fun SplashUIPreview() {
    SplashUI()
}