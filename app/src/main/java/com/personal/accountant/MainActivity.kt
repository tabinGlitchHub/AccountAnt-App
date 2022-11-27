package com.personal.accountant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.accountant.ui.theme.*
import java.lang.Exception


private val passCode = mutableStateOf("Enter passcode")
private val savedPassCode = "1111"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockScreen()
        }
    }
}

@Composable
fun LockScreen() {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val passCodeTxt by passCode
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryDark),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .width(screenWidth - 20.dp)
                .padding(0.dp, 150.dp, 0.dp, 0.dp), horizontalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = Color(0xff1a1a1a),
                modifier = Modifier.height(50.dp)
            ) {
                Text(
                    passCode.value,
                    modifier = Modifier
                        .width(screenWidth - 100.dp)
                        .absolutePadding(20.dp, 10.dp, 20.dp, 5.dp),
                    color = Color(0xFF676767),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(screenHeight - 580.dp))
        Column(
            modifier = Modifier
                .height(350.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LockButton(text = "1")
                LockButton(text = "2")
                LockButton(text = "3")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LockButton(text = "4")
                LockButton(text = "5")
                LockButton(text = "6")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LockButton(text = "7")
                LockButton(text = "8")
                LockButton(text = "9")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LockButton(text = "X")
                LockButton(text = "0")
                DeleteLastButton()
            }
        }

    }
}

@Composable
fun LockButton(text: String) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    Button(
        onClick = {
            if (text == "X") {
                passCode.value = "Enter passcode";
            } else {
                if (passCode.value.contains("Enter") || passCode.value.contains("Incorrect")) {
                    passCode.value = ""
                }
                passCode.value += text
                if (savedPassCode == passCode.value) {
                    passCode.value = "SUCCESS"
                    val intent = Intent(context, HomeActivity::class.java).apply {
                        this.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                    context.startActivity(intent)
                    activity?.finish()
                } else if (passCode.value.length >= 4) {
                    passCode.value = "Incorrect Passcode"
                }
            }
        },
        shape = CircleShape,
        modifier = Modifier
            .height(60.dp)
            .width(60.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = blendedPrimaryDark),
    ) {
        Text(text = text, color = subtleWhite, fontSize = 20.sp)
    }
}

@Composable
fun DeleteLastButton() {
    Button(
        onClick = {
            if (isPassNumeric()) {
                passCode.value = passCode.value.dropLast(1);
                if (passCode.value.isEmpty()) {
                    passCode.value = "Enter passcode";
                }
            } else {
                passCode.value = "Enter passcode";
            }
        },
        shape = CircleShape,
        modifier = Modifier
            .height(60.dp)
            .width(60.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = blendedPrimaryDark)
    ) {
        Text(text = "<", color = subtleWhite, fontSize = 20.sp)
    }
}

fun isPassNumeric(): Boolean {
    return try {
        passCode.value.toInt()
        true
    } catch (e: Exception) {
        false
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LockScreen()
}