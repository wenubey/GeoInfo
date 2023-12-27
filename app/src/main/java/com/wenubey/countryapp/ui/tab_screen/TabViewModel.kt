package com.wenubey.countryapp.ui.tab_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class TabViewModel(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {


    var currentIndex = mutableIntStateOf(savedStateHandle.get<Int>(SELECTED_TAB_INDEX_KEY) ?: 0)
        private set

    fun setIndex(index: Int) {
        currentIndex.intValue = index
        savedStateHandle[SELECTED_TAB_INDEX_KEY] = index
    }

}

private const val SELECTED_TAB_INDEX_KEY = "selected_tab_index_key"