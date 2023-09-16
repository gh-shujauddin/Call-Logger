package com.qadri.calllogger.ui.homescreen

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import androidx.lifecycle.ViewModel
import com.qadri.calllogger.data.CallData
import com.qadri.calllogger.data.LastSync
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LogScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LastSync())
    val uiState = _uiState.asStateFlow()

    private val _callList = MutableStateFlow(listOf<CallData>())
    val callList = _callList.asStateFlow()

    fun getCallLogs(context: Context) {
        _uiState.value = LastSync(convertLastSync())
        val c = context.applicationContext
        val projection = arrayOf(
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
        )
        val cursor = c.contentResolver.query(
            CallLog.Calls.CONTENT_URI.buildUpon()
                .build(),
            projection, null, null, "${CallLog.Calls.DATE} DESC"
        )

        _callList.value = cursorToMatrix(cursor)
    }


    private fun cursorToMatrix(cursor: Cursor?): List<CallData> {
        val matrix = mutableListOf<CallData>()
        cursor?.use {
            while (it.moveToNext()) {
                val list = CallData(
                    phoneNumber = it.getStringFromColumn(CallLog.Calls.CACHED_NAME)
                        ?: it.getStringFromColumn(
                            CallLog.Calls.NUMBER
                        ),
                    callType = when (it.getStringFromColumn(CallLog.Calls.TYPE).toInt()) {
                        1 -> "INCOMING"
                        2 -> "OUTGOING"
                        3 -> "MISSED"
                        4 -> "VOICEMAIL"
                        5 -> "DECLINED"
                        6 -> "BLOCKED"
                        else -> "OTHERS"
                    },
                    date = convertDate(it.getStringFromColumn(CallLog.Calls.DATE)),
                    duration = it.getStringFromColumn(CallLog.Calls.DURATION),
                    phoneNumberFrom = it.getStringFromColumn(CallLog.Calls.NUMBER),
                    fileName = "00918956231478(00918682715648)_20230912111230.mp3"
                )
                matrix.add(list)
            }
        }
        return matrix
    }

    private fun convertDate(callDate: String): String {
        var seconds: Long = callDate.toLong()
        var formatter = SimpleDateFormat("EEEE dd MMMM yyyy kk:mm:ss")
        return formatter.format(Date(seconds))
    }

    private fun convertLastSync(): String {
        var seconds = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd k:mm:ss")
        return formatter.format(seconds)
    }

    @SuppressLint("Range")
    private fun Cursor.getStringFromColumn(columnName: String) =
        getString(getColumnIndex(columnName))


}