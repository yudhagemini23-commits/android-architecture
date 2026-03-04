package com.ina17.android_architecture.features.hero.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ina17.android_architecture.features.hero.domain.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val repository: HeroRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow<HeroDetailState>(HeroDetailState.Loading)
    val state: StateFlow<HeroDetailState> = _state

    private val heroId: Int = checkNotNull(savedStateHandle["heroId"])

    init {
        getHeroDetail()
    }

    private fun getHeroDetail() {
        viewModelScope.launch {
            _state.value = HeroDetailState.Loading

            // Memanggil fungsi yang baru kita buat di Repository
            repository.getHerobyId(heroId).fold(
                onSuccess = { hero ->
                    _state.value = HeroDetailState.Success(hero)
                },
                onFailure = { error ->
                    _state.value = HeroDetailState.Error(error.message ?: "Terjadi kesalahan")
                }
            )
        }
    }
}