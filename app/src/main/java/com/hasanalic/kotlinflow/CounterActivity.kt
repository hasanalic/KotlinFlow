package com.hasanalic.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hasanalic.kotlinflow.ui.theme.KotlinFlowTheme

class CounterActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "firstScreen") {
                        composable(route = "firstScreen") {

                            val viewmodel = viewModel<FSViewModel>()
                            //val time by viewmodel.counter.collectAsState() // Lifecycle awareness (onPause, onStop vs bunu etkilemiyor.)
                            val time by viewmodel.counter.collectAsStateWithLifecycle() // Uygulama arkaplana geçince(onPause) 5 saniye sonra collect etmeyi keser.

                            FirstScreen(time = time) {
                                navController.navigate("secondScreen")
                            }
                        }
                        composable(route = "secondScreen") {
                            SecondScreen()
                        }
                    }

                }
            }
        }
    }
}