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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bingoapp.uiLayer.bingoList.components.BingoListItem

@Composable
fun BingoList(
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    editBingo: (Int) -> Unit,
    shareBingo: (String) -> Unit,
    viewModel: BingoListViewModel = hiltViewModel()
){
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
                Spacer(modifier = Modifier.height(20.dp))
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier.width(250.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(state.bingoList.reversed()) { bingo ->
                BingoListItem(
                    bingo = bingo,
                    onItemClick = onItemClick,
                    editBingo = editBingo,
                    deleteBingo = {id: Int -> viewModel.deleteBingo(id)},
                    getBingoShareData = {id: Int -> viewModel.shareData(id, shareBingo = shareBingo)},
                    switchExpanded = {id : Int -> viewModel.switchExpanded(id)},
                    expandedOff = {id : Int -> viewModel.expandedOff(id)}
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