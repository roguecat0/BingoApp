package com.example.bingoapp.uiLayer.bingoList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bingoapp.uiLayer.models.BingoOptionUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BingoListItem(
    bingo: BingoOptionUI,
    onItemClick: (Int) -> Unit,
    editBingo: (Int) -> Unit,
    deleteBingo: (Int) -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick(bingo.id)},
        modifier = Modifier
            .padding(8.dp)
            .width(310.dp)
            .height(60.dp),
        colors = CardDefaults
            .elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults
            .elevatedCardElevation(defaultElevation = 4.dp)

    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.End)){
            Text(
                text = bingo.name,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp, start = 17.dp, end = 13.dp)
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .width(130.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge
            )
            SuggestionChip(
                onClick = { /*TODO*/ },
                label = {Text(text = bingo.size)},
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically),
                shape = MaterialTheme.shapes.extraLarge

            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clickable { editBingo(bingo.id) }
                    .padding(10.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clickable { deleteBingo(bingo.id) }
                    .padding(5.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
//            TextButton(onClick = { editBingo(bingo.id) },
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .wrapContentHeight(Alignment.CenterVertically)
//
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Edit,
//                    contentDescription = null,
//                    modifier = Modifier.padding(end = 0.dp)
//                )
//            }
//            TextButton(onClick = { deleteBingo(bingo.id) },
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .wrapContentHeight(Alignment.CenterVertically)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Close,
//                    contentDescription = null,
//                    modifier = Modifier.padding(start = 0.dp)
//                )
//            }
        }
    }
}