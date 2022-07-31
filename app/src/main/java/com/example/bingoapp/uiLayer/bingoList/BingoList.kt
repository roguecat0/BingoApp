package com.example.bingoapp.uiLayer.bingoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bingoapp.uiLayer.bingoList.components.BingoListItem

@Composable
fun BingoListScreen(){
    val viewModel = BingoListViewModel()
    val state: BingoListState = viewModel.state
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.bingoList) { bingo ->
            BingoListItem(
                bingo = bingo
            )
        }
    }
}
@Preview(showBackground = true,
    widthDp = 200)
@Composable
fun BingoPreview() {
    Surface(modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.surface) {
        BingoListScreen()
    }

}