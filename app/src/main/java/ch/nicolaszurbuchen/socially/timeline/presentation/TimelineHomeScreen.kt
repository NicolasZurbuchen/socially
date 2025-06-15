package ch.nicolaszurbuchen.socially.timeline.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.Screen
import ch.nicolaszurbuchen.socially.common.components.model.SociallyErrorState
import ch.nicolaszurbuchen.socially.common.components.ui.SociallyErrorBox
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineHomeState
import ch.nicolaszurbuchen.socially.timeline.presentation.ui.TimelineHomePost

@Composable
fun TimelineHomeScreen(
    navController: NavController,
    viewModel: TimelineHomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val eventFlow = viewModel.eventFlow

    LaunchedEffect(true) {
        eventFlow.collect {
            navController.navigate(Screen.IntroWelcomeScreen.route) {
                popUpTo(0) { inclusive = true}
            }
        }
    }

    TimelineHomeScreenContent(
        state = state,
        onLogout = viewModel::signOut,
        onRefresh = viewModel::refresh,
        onLoadNextPage = viewModel::loadNextPage,
        onCreateNewPost = { navController.navigate(Screen.TimelineNewPostScreen.route) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineHomeScreenContent(
    state: TimelineHomeState,
    onLogout: () -> Unit,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    onCreateNewPost: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(R.drawable.img_socially_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_m))
                            .size(dimensionResource(R.dimen.size_level_3)),
                    )
                },
                actions = {
                    IconButton(
                        onClick = onLogout,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = dimensionResource(R.dimen.padding_m))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_m)),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_m)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(R.dimen.padding_m))
            ) {
                if (state.hasError) {
                    item {
                        SociallyErrorBox(
                            error = SociallyErrorState(
                                R.string.error_something_wrong,
                                R.string.timeline_new_post_cannot_load,
                            )
                        )
                    }
                }

                itemsIndexed(state.posts) { index, post ->
                    TimelineHomePost(
                        state = post,
                    )

                    if (index == state.posts.lastIndex - 2 && !state.isLoadingMore && state.hasMore) {
                        onLoadNextPage()
                    }
                }

                if (state.isLoadingMore) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.padding_l))
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = onCreateNewPost,
                modifier = Modifier
                    .padding(
                        end = dimensionResource(R.dimen.padding_l),
                        bottom = dimensionResource(R.dimen.padding_l),
                    )
                    .align(Alignment.BottomEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
}