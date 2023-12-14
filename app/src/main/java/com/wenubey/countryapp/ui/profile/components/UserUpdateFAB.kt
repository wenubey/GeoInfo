package com.wenubey.countryapp.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.countryapp.ui.theme.CountryAppTheme
import com.wenubey.countryapp.utils.Constants.PROFILE_UPDATE_FAB_DESCRIPTION

// TODO align for screen
@Composable
fun UserUpdateFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FABContent(onClick = onClick, modifier = modifier)
}

@Composable
private fun FABContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(onClick = onClick, modifier = modifier.padding(end = 24.dp, bottom = 24.dp)) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = PROFILE_UPDATE_FAB_DESCRIPTION,
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun FABContentPreview() {
     CountryAppTheme {
        Surface {
             FABContent()
        }
    }
}