package ch.nicolaszurbuchen.socially.timeline.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.Screen
import ch.nicolaszurbuchen.socially.common.ui.SociallyButtonPrimary
import ch.nicolaszurbuchen.socially.common.ui.SociallyElevatedButton
import ch.nicolaszurbuchen.socially.common.ui.SociallyTextField
import ch.nicolaszurbuchen.socially.timeline.presentation.model.TimelineNewPostState
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun TimelineNewPostScreen(
    navController: NavController,
    viewModel: TimelineNewPostViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val navigateToHome = {
        navController.navigate(Screen.TimelineHomeScreen.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.uploadImage(it) }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) launcher.launch("image/*")
        }
    )

    val pickImage = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch("image/*")
        } else {
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                launcher.launch("image/*")
            } else {
                permissionLauncher.launch(permission)
            }
        }
    }

    if (state.success) {
        AlertDialog(
            onDismissRequest = navigateToHome,
            confirmButton = {
                TextButton(onClick = navigateToHome) {
                    Text(stringResource(R.string.common_ok))
                }
            },
            title = { Text(stringResource(R.string.timeline_new_post_success_title)) },
            text = { Text(stringResource(R.string.timeline_new_post_success_message)) }
        )
    }

    TimelineNewPostContent(
        state = state,
        onNavigateUp = { navController.navigateUp() },
        onUploadImage = { pickImage() },
        onDeleteImage = viewModel::deleteImage,
        onPostChange = viewModel::updatePost,
        onPost = viewModel::post,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineNewPostContent(
    state: TimelineNewPostState,
    onNavigateUp: () -> Unit,
    onUploadImage: () -> Unit,
    onDeleteImage: () -> Unit,
    onPostChange: (String) -> Unit,
    onPost: () -> Unit,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.timeline_new_post_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        modifier = Modifier
            .imePadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(dimensionResource(R.dimen.padding_l))
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.medium,
                    )
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.size_level_20)),
            ) {
                if (state.isImageUploaded) {
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(state.imageUri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.medium)
                        )
                        SociallyElevatedButton(
                            icon = Icons.Default.Delete,
                            onClick = onDeleteImage,
                            enabled = state.interactionEnabled,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.size_level_4))
                                .padding(
                                    end = dimensionResource(R.dimen.padding_s),
                                    bottom = dimensionResource(R.dimen.padding_s),
                                ),
                        )
                    }
                } else {
                    val columnModifier = if (state.interactionEnabled) Modifier
                        .fillMaxSize()
                        .clickable { onUploadImage() }
                    else Modifier.fillMaxSize()

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = columnModifier,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_image_placeholder),
                            contentDescription = null,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.size_level_7)),
                        )
                        Text(
                            text = stringResource(R.string.timeline_new_post_upload_image),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = dimensionResource(R.dimen.padding_m))
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.timeline_new_post_header),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_l))
            )

            SociallyTextField(
                value = state.post,
                onValueChange = {
                    onPostChange(it)
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                },
                enabled = state.interactionEnabled,
                maxLines = Int.MAX_VALUE,
                placeholder = stringResource(R.string.timeline_new_post_type_here),
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_s))
                    .defaultMinSize(minHeight = dimensionResource(R.dimen.size_level_9))
                    .bringIntoViewRequester(bringIntoViewRequester),
            )

            SociallyButtonPrimary(
                text = stringResource(R.string.timeline_new_post_post),
                onClick = onPost,
                enabled = state.isPostButtonEnabled,
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.padding_m))
                    .height(dimensionResource(R.dimen.size_level_7)),
            )

            if (state.isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = dimensionResource(R.dimen.padding_l)),
                    )
                }
            }
        }
    }
}