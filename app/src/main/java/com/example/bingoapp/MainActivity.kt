package com.example.bingoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bingoapp.uiLayer.BingoApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BingoApp(shareBingo = {bingoValues: String -> shareBingo(bingoValues)})
        }
    }

    private fun shareBingo(bingoValues: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val sendStr = "https://www.bingo.be/$bingoValues"
            Log.i("activity",sendStr)
            putExtra(Intent.EXTRA_TEXT, sendStr)
            putExtra(Intent.EXTRA_TITLE, "Import Bingo")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}

@Preview(showBackground = true,
    widthDp = 200)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        //BingoListScreen()
    }
}