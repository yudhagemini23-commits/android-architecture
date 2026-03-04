package com.ina17.android_architecture.features.hero.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ina17.android_architecture.features.hero.domain.model.Hero
import com.ina17.android_architecture.ui.theme.AndroidarchitectureTheme

@Composable
fun HeroDetailScreen(
    viewModel: HeroDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is HeroDetailState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HeroDetailState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = currentState.message, color = MaterialTheme.colorScheme.error)
            }
        }
        is HeroDetailState.Success -> {
            HeroDetailContent(
                hero = currentState.hero,
                onBackClick = onBackClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroDetailContent(
    hero: Hero,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { hero.localizedName?.let { Text(it) } },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = hero.img, // Pastikan field ini ada di model Hero Anda
                contentDescription = hero.localizedName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            hero.localizedName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Text(
                text = "Primary Attribute: ${hero.primaryAttr?.uppercase()}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Attack Type: ${hero.attackType}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroDetailContentPreview(){
    AndroidarchitectureTheme {
        val dummyHero =Hero(
            id = 1,
            localizedName = "Anti-Mage",
            primaryAttr = "agi",
            attackType = "Melee",
            img = "https://api.opendota.com/apps/dota2/images/dota_react/heroes/antimage.png?",
            icon = null
        )
        HeroDetailContent(
            hero = dummyHero,
            onBackClick = {}
        )
    }
}
