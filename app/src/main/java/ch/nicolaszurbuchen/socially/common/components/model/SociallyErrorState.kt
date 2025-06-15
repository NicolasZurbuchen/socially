package ch.nicolaszurbuchen.socially.common.components.model

import androidx.annotation.StringRes

data class SociallyErrorState(
    @StringRes val title: Int,
    @StringRes val description: Int,
)
