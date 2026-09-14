package com.navin.personalos.core.database

/** Read projection only; ordinary history stays in its authoritative entity tables. */
data class HistoryEntry(
    val key: String,
    val type: EntityType?,
    val entityId: String?,
    val localDate: String,
    val occurredAt: Long,
    val title: String,
    val description: String,
    val lifeAreaId: String?,
    val projectId: String?,
    val goalId: String?,
)
