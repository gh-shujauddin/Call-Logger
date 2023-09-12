package com.qadri.calllogger.ui

import androidx.lifecycle.ViewModel
import com.qadri.calllogger.data.CallList
import com.qadri.calllogger.data.CallListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LogScreenViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(CallList())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = CallList(
            callList = CallListData.callListData,
            lastSync = "2023-09-09 16:11:45"
        )
    }


}