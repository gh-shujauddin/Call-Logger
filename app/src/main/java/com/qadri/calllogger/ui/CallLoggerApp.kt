@file:OptIn(ExperimentalMaterial3Api::class)

package com.qadri.calllogger.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qadri.calllogger.R
import com.qadri.calllogger.ui.homescreen.LogScreen
import com.qadri.calllogger.ui.homescreen.LogScreenViewModel

@Composable
fun CallLoggerApp(
    viewModel: LogScreenViewModel
) {
    val lastSync = viewModel.uiState.collectAsState().value.lastSync
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Surface(shadowElevation = dimensionResource(id = R.dimen.padding_medium)) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    title = {
                        Box(
                            Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(
                                    text = stringResource(id = R.string.call_logs),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.last_sync,
                                        lastSync
                                    ), fontSize = 18.sp
                                )
                            }
                        }
                    },
                    actions = {
                        Box(
                            Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row {
                                IconButton(onClick = {
                                    makeToast(
                                        "Settings",
                                        context
                                    )
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Setting"
                                    )
                                }
                                IconButton(onClick = {
                                    viewModel.getCallLogs(context)
                                    makeToast("Sync Completed", context)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Refresh"
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        LogScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

private fun makeToast(string: String, context: Context) {
    Toast.makeText(context, string, Toast.LENGTH_LONG).show()
}
