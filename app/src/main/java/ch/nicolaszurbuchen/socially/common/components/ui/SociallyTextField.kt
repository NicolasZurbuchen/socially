package ch.nicolaszurbuchen.socially.common.components.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ch.nicolaszurbuchen.socially.R
import ch.nicolaszurbuchen.socially.ui.theme.SociallyTheme

@Composable
fun SociallyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: Painter? = null,
    maxLines: Int = 1,
    supportingText: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityToggle: () -> Unit = {},
) {
    val visualTransformation = when {
        isPassword && !passwordVisible -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        placeholder = { Text(placeholder) },
        leadingIcon = leadingIcon?.let {
            {
                Image(
                    painter = leadingIcon,
                    contentDescription = null,
                )
            }
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(
                    onClick = onPasswordVisibilityToggle,
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Visibility",
                    )
                }
            }
        } else null,
        supportingText = { supportingText?.let { Text(it) } },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        maxLines = maxLines,
        singleLine = maxLines == 1,
        shape = MaterialTheme.shapes.small,
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .fillMaxWidth(),
    )
}

@Preview
@Composable
private fun SociallyTextFieldPreview() {
    SociallyTheme {
        var value by remember { mutableStateOf("") }
        SociallyTextField(
            value = value,
            onValueChange = { value = it },
            placeholder = "Username",
            leadingIcon = painterResource(R.drawable.ic_user_on),
        )
    }
}