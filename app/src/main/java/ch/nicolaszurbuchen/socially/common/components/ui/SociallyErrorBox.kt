package ch.nicolaszurbuchen.socially.common.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.common.components.model.SociallyErrorState
import ch.nicolaszurbuchen.socially.common.theme.SociallyTheme

@Composable
fun SociallyErrorBox(
    error: SociallyErrorState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = MaterialTheme.shapes.small,
            )
            .padding(dimensionResource(R.dimen.padding_m))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(error.title),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onErrorContainer,
        )
        Text(
            text = stringResource(error.description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_xs))
        )
    }
}

@Preview
@Composable
private fun SociallyErrorBoxPreview() {
    SociallyTheme {
        SociallyErrorBox(
            SociallyErrorState(
                title = R.string.error_something_wrong,
                description = R.string.error_something_wrong_description,
            )
        )
    }
}