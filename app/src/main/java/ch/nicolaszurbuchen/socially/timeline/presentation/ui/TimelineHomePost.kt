package ch.nicolaszurbuchen.socially.timeline.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomePostState
import ch.nicolaszurbuchen.socially.common.theme.SociallyTheme
import ch.nicolaszurbuchen.socially.common.utils.toReadableDate
import coil.compose.SubcomposeAsyncImage

@Composable
fun TimelineHomePost(
    state: TimelineHomePostState,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
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
                        .size(dimensionResource(R.dimen.size_level_4))
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape,
                        )
                ) {
                    Text(
                        text = state.usernameLetter.toString(),
                        color = Color.White,
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
                        text = state.timestamp.toReadableDate(),
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
                SubcomposeAsyncImage(
                    model = state.imageUrl,
                    contentDescription = null,
                    loading = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.secondary,
                                    shape = MaterialTheme.shapes.medium,
                                ),
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_image_placeholder),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(vertical = dimensionResource(R.dimen.padding_l))
                                    .size(dimensionResource(R.dimen.size_level_7)),
                            )
                        }
                    },
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