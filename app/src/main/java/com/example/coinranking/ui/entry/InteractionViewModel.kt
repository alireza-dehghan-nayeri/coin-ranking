package com.example.coinranking.ui.entry

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InteractionViewModel @Inject constructor(private val stateHandle: SavedStateHandle) :
    ViewModel() {

    val splashSeen: Boolean
        get() = stateHandle[SPLASH_SEEN] ?: false

    fun setSplashSeen() {
        stateHandle[SPLASH_SEEN] = true
    }

    companion object {
        const val SPLASH_SEEN = "splash_seen"
    }
}