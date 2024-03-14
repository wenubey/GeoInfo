package com.wenubey.geoinfo.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wenubey.geoinfo.R
import com.wenubey.geoinfo.ui.theme.GeoInfoAppTheme


@Composable
fun UserUpdateFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        modifier = modifier
            .padding(end = 24.dp, bottom = 24.dp)
            .testTag(stringResource(id = R.string.user_update_fab_test_tag)),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(id = R.string.country_flag_content_description),
        )
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun FABContentPreview() {
    GeoInfoAppTheme {
        Surface {
            UserUpdateFAB()
        }
    }
}