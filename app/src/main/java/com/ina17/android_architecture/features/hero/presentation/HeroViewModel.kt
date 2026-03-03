package com.ina17.android_architecture.features.hero.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ina17.android_architecture.features.hero.domain.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(
    private val repository: HeroRepository
): ViewModel(){
    private val _state = MutableStateFlow<HeroState>(HeroState.Loading)
    val state: StateFlow<HeroState> = _state

    init {
        fetchHeroes()
    }

    private fun fetchHeroes(){
        viewModelScope.launch {
            _state.value = HeroState.Loading
            repository.getHeroes().fold(
                onSuccess = { heroes ->
                    _state.value = HeroState.Success(heroes)
                },
                onFailure = { error ->
                    _state.value = HeroState.Error(error.message ?: "Error occured")
                }
            )
        }
    }
}