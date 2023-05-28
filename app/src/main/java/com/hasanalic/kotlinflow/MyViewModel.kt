package com.hasanalic.kotlinflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

    init {
        collectInViewModel()
    }

    private fun collectInViewModel() {
        viewModelScope.launch {
            countDownTimer
                .filter {
                    it % 3 == 0
                }
                .map {
                    it * it
                }
                .collect {
                    println("Counter is : $it")
                }

            // flow'da yeni bir değer yayılırsa(emit edilirse), önceki değer iptal edilir ve yeni değer alınır.
            // Aşağıdakinin sonucu sadece 0 olur.
            // Çünkü collectLatest'in delay'i flow'dan fazla olduğu için sürekli yeni değeri alır ve eskisini siler ve en son değer olan 0 kalır.
            countDownTimer.collectLatest {
                delay(2000)
                println(it)
            }
        }

        /*
        // Aşağıdaki yöntemle yukarıdaki yöntem(filter ve map hariç) aynı şeyi yaparlar.
        countDownTimer.onEach {
            println("Counter is : $it")
        }.launchIn(viewModelScope)
         */
    }

    // LIVE DATA
    private val _liveData = MutableLiveData<String>("MyLiveData")
    val liveData: LiveData<String> = _liveData

    fun changeLiveDataValue() {
        _liveData.value = "Live Data"
    }

    // STATE FLOW
    private val _stateFlow = MutableStateFlow("MyStateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeStateFlowValue() {
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }

}