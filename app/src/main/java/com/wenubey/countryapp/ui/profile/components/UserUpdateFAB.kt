package com.wenubey.countryapp.ui.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.wenubey.countryapp.utils.Constants.PROFILE_UPDATE_FAB_DESCRIPTION

@Composable
fun UserUpdateFAB(
    onClick: () -> Unit
) {
    FloatingActionButton(onClick =onClick) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = PROFILE_UPDATE_FAB_DESCRIPTION,
        )
    }
}