package com.personal.accountant

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.accountant.ui.theme.fontColor
import com.personal.accountant.ui.theme.primaryDark
import com.personal.accountant.ui.theme.primaryDarkVariant
import com.personal.accountant.ui.theme.primaryMarine

private val displayVal = mutableStateOf("...")
private val TAG = "HomeActivity"


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpPermission()
        setContent {
            HomeScreen()
        }
    }

    private fun setUpPermission() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted yet")
            makeRequest()
        } else {
            getLastMessage()
        }
    }

    private fun makeRequest() {
        requestPermissionLauncher.launch(
            Manifest.permission.READ_SMS
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i(TAG, "Granted")
                getLastMessage()
            } else {
                Log.i(TAG, "Denied")
            }
        }

    private fun getLastMessage() {
        val cursor: Cursor =
            this.contentResolver.query(Uri.parse("content://sms"), null, null, null, null)!!
        cursor.moveToFirst();

        try {
            displayVal.value = cursor.getString(12)
        } catch (e: Exception) {
            displayVal.value = "No SMS found."
        }

    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(primaryDarkVariant)
    ) {
        TopAppBar(
            title = {
                Column() {
                    Text("AccountAnt", color = primaryMarine)
                    Text("Search", color = Color(0xff989898), fontSize = 14.sp)
                }
            },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
//                IconButton(onClick = { /* doSomething() */ }) {
//                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
//                }
            }, backgroundColor = primaryDark, elevation = 7.dp, modifier = Modifier.height(70.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
//                .fillMaxWidth()
                .padding(7.dp)
                .widthIn(0.dp, 400.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color(0xff1c1c1c))
                    .padding(0.dp, 7.dp)
            ) {
                ExpandableCard()

            }

        }
    }
}

@Composable
fun ExpandableCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryDark)
            .padding(13.dp, 7.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .widthIn(300.dp, 400.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column() {
                Text(text = "07/11/2022", color = fontColor)
                Text(text = "07:45 pm", color = fontColor)
            }
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color(0xff707070))
                    .width(1.dp)
            )
            Column() {
                Text(text = "INR 3,000", color = fontColor)
            }
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .background(Color(0xff707070))
                    .width(1.dp)
            )
            Column() {
                Text(text = "Steam Inc.", color = fontColor)
            }
        }
    }
    Text(
        displayVal.value,
        modifier = Modifier
            .background(primaryDarkVariant)
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 14.dp,
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 14.dp
                )
            ).padding(5.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    HomeScreen()
}