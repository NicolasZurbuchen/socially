package ch.nicolaszurbuchen.socially.common.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.nicolaszurbuchen.socially.common.theme.SociallyTheme

@Composable
fun SociallyButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = text,
        )
    }
}

@Preview
@Composable
private fun SociallyButtonPrimaryPreview() {
    SociallyTheme {
        SociallyButtonPrimary(
            text = "Sign In",
            onClick = {},
        )
    }
}