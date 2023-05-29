package com.hasanalic.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hasanalic.kotlinflow.ui.theme.KotlinFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowTheme {
                val viewModel: MyViewModel by viewModels()
                ScreenTwo(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ScreenOne(viewModel: MyViewModel) {

    val counter = viewModel.countDownTimer.collectAsState(initial = 10)

    Surface(color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = counter.value.toString(),
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ScreenTwo(viewModel: MyViewModel) {

    val liveDataValue = viewModel.liveData.observeAsState()
    val stateFlowValue = viewModel.stateFlow.collectAsState()
    val sharedFlowValue = viewModel.sharedFlow.collectAsState(initial = "")
    
    Surface(color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(text = liveDataValue.value ?: "")
                Button(onClick = { viewModel.changeLiveDataValue() }) {
                    Text(text = "LiveData Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(text = stateFlowValue.value)
                Button(onClick = { viewModel.changeStateFlowValue() }) {
                    Text(text = "StateFlow Button")
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(text = sharedFlowValue.value)
                Button(onClick = { viewModel.changeSharedFlowValue() }) {
                    Text(text = "SharedFlow Button")
                }
            }
        }
    }
    
}