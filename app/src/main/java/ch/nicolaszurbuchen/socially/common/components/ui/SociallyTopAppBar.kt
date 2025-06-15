package ch.nicolaszurbuchen.socially.common.components.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.ui.theme.SociallyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SociallyTopAppBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            SociallyElevatedButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = onNavigateUp,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.padding_l))
                    .size(dimensionResource(R.dimen.size_level_4)),
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun SociallyTopAppBarPreview() {
    SociallyTheme {
        SociallyTopAppBar(
            onNavigateUp = {},
        )
    }
}