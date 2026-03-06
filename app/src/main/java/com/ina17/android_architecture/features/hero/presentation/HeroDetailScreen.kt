package com.ina17.android_architecture.features.hero.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Back")  },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            when (val currentState = state) {
                is HeroDetailState.Loading -> {
                    SkeletonHeroDetail()
                }

                is HeroDetailState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = currentState.message, color = MaterialTheme.colorScheme.error)
                    }
                }

                is HeroDetailState.Success -> {
                    HeroDetailContent(
                        hero = currentState.hero
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroDetailContent(
    hero: Hero
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = hero.img,
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
            hero = dummyHero
        )
    }
}

@Composable
fun SkeletonHeroDetail(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Skeleton main image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmerEffect()
        ){}

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .shimmerEffect()){}

        Spacer(modifier = Modifier.height(6.dp))

        Box(modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .shimmerEffect()){}

        Spacer(modifier = Modifier.height(6.dp))

        Box(modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .shimmerEffect()){}
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonHeroDetailPreview(){
    AndroidarchitectureTheme{
        SkeletonHeroDetail()
    }
}
