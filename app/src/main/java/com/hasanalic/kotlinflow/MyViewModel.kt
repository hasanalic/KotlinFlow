package com.hasanalic.kotlinflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MyViewModel: ViewModel() {

    val countDownTimer = flow<Int> {
        val countDownFrom = 10
        var counter = countDownFrom

        emit(countDownFrom)

        while (counter > 0) {
            delay(1000)
            counter--
            emit(counter)
        }
    }
}