package com.example.bingoapp.uiLayer.bingoList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bingoapp.dataLayer.dataSource.ListItems
import com.example.bingoapp.dataLayer.dataSource.toOptionUI
import com.example.bingoapp.uiLayer.bingoList.components.BingoListItem

@Composable
fun BingoList(onItemClick: (Int) -> Unit,
            onFabClick: () -> Unit,
            viewModel: BingoListViewModel = viewModel(),
            editBingo: (Int) -> Unit){
    val state: BingoListState = viewModel.state
    Box{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                Text(
                    text = "Bingo List",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            items(state.bingoList.reversed()) { bingo ->
                BingoListItem(
                    bingo = bingo,
                    onItemClick = onItemClick,
                    editBingo = editBingo,
                    deleteBingo = {id: Int -> viewModel.deleteBingo(id)}
                )
            }
            item{
                Spacer(Modifier.height(100.dp))
            }
        }
        FloatingActionButton(onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(50.dp)) {
            Icon(Icons.Filled.Add,"")
        }
    }

}
@Preview(showBackground = true,
    widthDp = 390)
@Composable
fun BingoPreview() {
    val bingoList = ListItems.BingoList.map { it.toOptionUI() }
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                Text(
                    text = "Bingo List",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            items(bingoList) { bingo ->
                BingoListItem(
                    bingo = bingo,
                    onItemClick = {  },
                    editBingo = {  },
                    deleteBingo = {}
                )
            }
            item{
                Spacer(Modifier.height(100.dp))
            }
        }
    }

}