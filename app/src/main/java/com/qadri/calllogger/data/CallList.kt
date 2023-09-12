package com.qadri.calllogger.data

data class CallList(
    val callList: List<CallData> = listOf(),
    val lastSync: String = ""
)

data class CallData(
    val id: Int,
    val phoneNumber: String,
    val callType: String,
    val date: String,
    val duration: Int,
    val phoneNumberFrom: String,
    val fileName: String
)
