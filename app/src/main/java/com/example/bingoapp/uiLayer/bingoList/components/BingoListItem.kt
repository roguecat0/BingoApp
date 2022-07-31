package com.example.bingoapp.uiLayer.bingoList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bingoapp.uiLayer.models.BingoOptionUI

@Composable
fun BingoListItem(
    bingo: BingoOptionUI
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${bingo.name}",
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${bingo.size.get(0)}x${bingo.size.get(1)}",
            color = Color.Red,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}