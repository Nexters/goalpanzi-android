package com.goalpanzi.mission_mate.feature.board.verification.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType

@Immutable
data class VerificationUiState(
    val nickname: String,
    val characterType: CharacterType?,
    val items: List<VerificationItemState>,
    val position: Int
) {
    @Stable
    sealed interface VerificationItemState {

        @Immutable
        data object Loading : VerificationItemState

        @Immutable
        data class Success(
            val verifiedAt: String,
            val image: String
        ) : VerificationItemState
    }
}
