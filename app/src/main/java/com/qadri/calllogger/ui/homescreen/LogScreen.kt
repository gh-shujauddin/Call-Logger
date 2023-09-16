@file:OptIn(ExperimentalMaterial3Api::class)

package com.qadri.calllogger.ui.homescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qadri.calllogger.R
import com.qadri.calllogger.data.CallData

@Composable
fun LogScreen(
    modifier: Modifier = Modifier,
    viewModel: LogScreenViewModel
) {
    val context = LocalContext.current
    viewModel.getCallLogs(context = context)
    val callLog = viewModel.callList.collectAsState().value

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(callLog) { callData ->
                LogCard(
                    modifier = modifier,
                    callData = callData,
                    playAudio = {
                        makeToast(context = context)
                    }
                )
            }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    top = dimensionResource(id = R.dimen.padding_medium),
                    bottom = dimensionResource(id = R.dimen.padding_large)
                )
                .shadow(4.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(CornerSize(8.dp))
        ) {
            Text(text = stringResource(R.string.upload), fontSize = 18.sp)
        }
    }

}

@Composable
fun LogCard(modifier: Modifier, callData: CallData, playAudio: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(4.dp)),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier.padding(
                dimensionResource(id = R.dimen.padding_large)
            )
        ) {
            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Max)

            ) {
                Text(text = callData.phoneNumber, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))

                Surface(
                    shape = RoundedCornerShape(corner = CornerSize(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier.width(60.dp),

                    ) {
                    Text(
                        text = callData.callType.uppercase(),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 7.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.data_s, callData.date),
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.duration, callData.duration),
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }
            Text(
                text = stringResource(R.string.fromPh, callData.phoneNumberFrom),
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
            )

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    shape = RoundedCornerShape(CornerSize(8.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(6.dp)
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    Text(text = callData.fileName, Modifier.padding(start = 8.dp))
                }
                Card(
                    onClick = playAudio,
                    shape = RoundedCornerShape(CornerSize(8.dp)),
                    modifier = Modifier.padding(start = 8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.play_arrow),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.secondary
                                ),
                                shape = RoundedCornerShape(CornerSize(8.dp))
                            )
                            .padding(3.dp)
                            .size(30.dp),
                    )
                }


            }
        }
    }
}

private fun makeToast(context: Context) {
    Toast.makeText(context, "Play Audio", Toast.LENGTH_LONG).show()
}

@Preview
@Composable
fun LogCardPreview() {
    LogCard(
        modifier = Modifier,
        callData = CallData(
            phoneNumber = "+919382715648",
            callType = "outgoing",
            date = "Tuesday 12 September 2023 11:12:26",
            duration = "2",
            phoneNumberFrom = "+918682715648",
            fileName = "00919382715648(00918682715648)_20230912111230.mp3"
        ),
        playAudio = {}
    )
}