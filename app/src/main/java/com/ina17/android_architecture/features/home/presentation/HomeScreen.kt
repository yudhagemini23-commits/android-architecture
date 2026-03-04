package com.ina17.android_architecture.features.home.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ina17.android_architecture.ui.theme.AndroidarchitectureTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
){
    Button(onClick = onNavigate,
        modifier = Modifier.padding(16.dp,)
    ){
        Text("Show Hero")
    }
}

@Preview (showBackground = true)
@Composable
fun  HomeScreenPreview(){
    AndroidarchitectureTheme {
        HomeScreen(
            onNavigate = {}
        )
    }
}