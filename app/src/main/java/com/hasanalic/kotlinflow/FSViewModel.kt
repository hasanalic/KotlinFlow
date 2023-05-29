package com.hasanalic.kotlinflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class FSViewModel: ViewModel() {

    private var count = 0
    val counter = flow {
        while (true) {
            delay(1000)
            emit(count++)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)
    // SharingStarted.WhileSubscribed(5000) -> kullanıcı başka bir ekrana geçerse bu flow 5 saniye boyunca
    // yayın yapmaya devam eder ve sonra durur
}