package com.qadri.calllogger.data

data class LastSync(
    val lastSync: String = ""
)

data class CallData(
    val phoneNumber: String,
    val callType: String,
    val date: String,
    val duration: String,
    val phoneNumberFrom: String,
    val fileName: String
)

data class CallLog(
    val CACHED_NAME: String,
    val NUMBER: String,
    val TYPE: String,
    val DATE: String,
    val DURATION: String
)


