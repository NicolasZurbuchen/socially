package ch.nicolaszurbuchen.socially.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.nicolaszurbuchen.socially.ui.theme.SociallyTheme

@Composable
fun SociallyButtonSecondary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        ),
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
private fun SociallyButtonSecondaryPreview() {
    SociallyTheme {
        SociallyButtonSecondary(
            text = "Sign In",
            onClick = {},
        )
    }
}