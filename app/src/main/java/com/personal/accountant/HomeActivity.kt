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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

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
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.RECORD_AUDIO)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted yet")
            makeRequest()
        }else{
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
        val cursor : Cursor = this.contentResolver.query(Uri.parse("content://sms"),null,null, null, null)!!
        cursor.moveToFirst();

        try {
            displayVal.value = cursor.getString(12)
        }catch (e : Exception){
            displayVal.value = "No SMS found."
        }

    }
}

@Composable
fun HomeScreen() {
    Text(displayVal.value)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    HomeScreen()
}