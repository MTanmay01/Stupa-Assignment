package com.mtanmay.stupa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mtanmay.stupa.navigation.RootNavGraph
import com.mtanmay.stupa.ui.theme.StupaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StupaTheme {
                val navController = rememberNavController()
                RootNavGraph(
                    navController = navController
                )
            }
        }
    }

}