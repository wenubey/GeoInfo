package com.wenubey.geoinfo.ui.tab_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.wenubey.geoinfo.utils.Constants.SELECTED_TAB_INDEX_KEY

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
