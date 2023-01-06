package com.lexwilliam.core.model

data class UserPresentation(
    val uid: String,
    val name: String,
    val photoUrl: String,
    val packs: List<PackInfoPresentation>
)