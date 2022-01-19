package com.lexwilliam.hanki.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TypographyPreview() {
    Column {
        Text("Testing", style = MaterialTheme.typography.h1)
        Text("Testing", style = MaterialTheme.typography.h2)
        Text("Testing", style = MaterialTheme.typography.h3)
        Text("Testing", style = MaterialTheme.typography.h4)
        Text("Testing", style = MaterialTheme.typography.h5)
        Text("Testing", style = MaterialTheme.typography.h6)
        Text("Testing", style = MaterialTheme.typography.subtitle1)
        Text("Testing", style = MaterialTheme.typography.subtitle2)
        Text("Testing", style = MaterialTheme.typography.body1)
        Text("Testing", style = MaterialTheme.typography.body2)
        Text("Testing", style = MaterialTheme.typography.button)
        Text("Testing", style = MaterialTheme.typography.caption)
        Text("Testing", style = MaterialTheme.typography.overline)
    }
}