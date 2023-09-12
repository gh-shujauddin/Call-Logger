@file:OptIn(ExperimentalMaterial3Api::class)

package com.qadri.calllogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qadri.calllogger.ui.theme.CallLoggerTheme
import com.qadri.calllogger.ui.LogScreen
import com.qadri.calllogger.ui.LogScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CallLoggerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<LogScreenViewModel>()
                    Scaffold(
                        topBar = {
                            Surface(shadowElevation = dimensionResource(id = R.dimen.padding_medium)) {
                                TopAppBar(
                                    colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.background),
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
                                                        "2023-08-29 11:12:26"
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
                                                IconButton(onClick = { /*TODO*/ }) {
                                                    Icon(
                                                        imageVector = Icons.Default.Settings,
                                                        contentDescription = "Setting"
                                                    )
                                                }
                                                IconButton(onClick = { /*TODO*/ }) {
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
            }
        }
    }
}