package com.example.bingoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bingoapp.ui.theme.BingoAppTheme
import com.example.bingoapp.uiLayer.BingoApp
import com.example.bingoapp.uiLayer.bingoList.BingoListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingoAppTheme {
                BingoApp()
            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

@Preview(showBackground = true,
    widthDp = 200)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
        //color = MaterialTheme.colorScheme.background
    ) {
        BingoListScreen()
    }
}