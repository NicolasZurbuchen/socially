package ch.nicolaszurbuchen.socially.timeline.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomePostState
import ch.nicolaszurbuchen.socially.ui.theme.SociallyTheme
import coil.compose.AsyncImage

@Composable
fun TimelineHomePost(
    state: TimelineHomePostState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_m)),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape,
                        )
                ) {
                    Text(
                        text = state.usernameLetter.toString(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = dimensionResource(R.dimen.padding_m)),
                ) {
                    Text(
                        text = state.username,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = state.timestamp.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.padding_xs)),
                    )
                }
            }

            if (!state.post.isNullOrEmpty()) {
                Text(
                    text = state.post,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.padding_m))
                )
            }

            if (state.hasImage) {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .padding(top = if (state.hasText) dimensionResource(R.dimen.padding_m) else dimensionResource(R.dimen.padding_s)),
                )
            }
        }
    }
}

@Preview
@Composable
private fun TimelineHomePostPreview() {
    SociallyTheme {
        val state = TimelineHomePostState()

        TimelineHomePost(
            state = state,
        )
    }
}