package com.goalpanzi.mission_mate.core.domain.common.model.mission

enum class MissionStatus {
    CREATED,
    CANCELED,
    IN_PROGRESS,
    DELETED,
    PENDING_COMPLETION,
    COMPLETED;

    companion object {
        val statusString: String = listOf(CREATED, CANCELED, IN_PROGRESS, PENDING_COMPLETION)
            .joinToString(",") { it.name }
    }
}


