package com.ina17.android_architecture.features.hero.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
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
fun HeroScreen(
    viewModel: HeroViewModel = hiltViewModel(),
    onHeroClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showSortDialog by remember { mutableStateOf(false) }

    HeroList(
        state=state,
        onHeroClick = onHeroClick,
        onSortOptionSelected = { selectedSortType ->
            viewModel.sortHeroes(selectedSortType)
        }
    )
}

@Composable
fun HeroList(
    state: HeroState,
    onHeroClick: (Int) -> Unit,
    onSortOptionSelected: (SortType) -> Unit
){

    var isMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Dota 2 Heroes") },
                actions = {
                    Box {
                        IconButton(onClick = { isMenuExpanded = true }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Urutkan")
                        }

                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = {
                                isMenuExpanded = false
                            }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Name (A - Z)") },
                                onClick = {
                                    isMenuExpanded = false
                                    onSortOptionSelected(SortType.NAME_ASCENDING) // Kirim aksi
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Name (Z - A)") },
                                onClick = {
                                    isMenuExpanded = false
                                    onSortOptionSelected(SortType.NAME_DESCENDING)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Primary Attribute") },
                                onClick = {
                                    isMenuExpanded = false
                                    onSortOptionSelected(SortType.PRIMARY_ATTRIBUTE)
                                }
                            )
                            DropdownMenuItem(
                                text = {Text("Attack Type")},
                                onClick = {
                                    isMenuExpanded = false
                                    onSortOptionSelected(SortType.ATTACK_TYPE)
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is HeroState.Loading -> {
                    CircularProgressIndicator()
                }
                is HeroState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is HeroState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.heroes) { hero ->
                            HeroItemCard(hero = hero,
                                onClick = {onHeroClick(hero.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListPreview(){
    AndroidarchitectureTheme {
        // 1. Buat data dummy
        val dummyHero = listOf(
            Hero(
                id = 1,
                localizedName = "Anti-Mage",
                primaryAttr = "agi",
                attackType = "Melee",
                img = "https://api.opendota.com/apps/dota2/images/dota_react/heroes/antimage.png?",
                icon = "https://cdn.cloudflare.steamstatic.com/Anti-Mage"
            ),
            Hero(
                id = 2,
                localizedName = "Axe",
                primaryAttr = "str",
                attackType = "Melee",
                img = "https://api.opendota.com/apps/dota2/images/dota_react/heroes/axe.png?",
                icon = null
            ),
            Hero(
                id = 3,
                localizedName = "Crystal Maiden",
                primaryAttr = "int",
                attackType = "Ranged",
                img = "https://api.opendota.com/apps/dota2/images/dota_react/heroes/crystal_maiden.png?",
                icon = null
            )
        )
        val dummyState = HeroState.Success(dummyHero)
        HeroList(
            state = dummyState,onHeroClick = {}, onSortOptionSelected = {}
        )
    }
}

@Composable
fun HeroItemCard(hero: Hero,
                 onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClick()},
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = hero.img,
                    contentDescription = "Gambar dari ${hero.localizedName}",
                    modifier = Modifier
                        .width(86.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Row(modifier = Modifier
                    .fillMaxSize(),
                    verticalAlignment = Alignment.Top
                )
                {
                    AsyncImage(
                        model = hero.icon,
                        contentDescription = "Ikon dari ${hero.localizedName}",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        horizontalAlignment = AbsoluteAlignment.Left
                    )
                    {
                        hero.localizedName?. let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        hero.localizedName?. let {
                            Text(
                                text = "Primary Attribute: ${hero.primaryAttr?.uppercase()}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(
                            text = "Attack Type: ${hero.attackType}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
