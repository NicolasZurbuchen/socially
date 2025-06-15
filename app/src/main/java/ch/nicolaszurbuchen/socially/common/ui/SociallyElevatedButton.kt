package ch.nicolaszurbuchen.socially.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.ui.theme.SociallyTheme

@Composable
fun SociallyElevatedButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
) {
    ElevatedButton (
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = dimensionResource(R.dimen.elevation_level_3),
            pressedElevation = dimensionResource(R.dimen.elevation_level_3),
            focusedElevation = dimensionResource(R.dimen.elevation_level_3),
            hoveredElevation = dimensionResource(R.dimen.elevation_level_3),
            disabledElevation = dimensionResource(R.dimen.elevation_level_4)
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun SociallyElevatedButtonPreview() {
    SociallyTheme {
        SociallyElevatedButton(
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = {},
            shape = CircleShape,
        )
    }
}