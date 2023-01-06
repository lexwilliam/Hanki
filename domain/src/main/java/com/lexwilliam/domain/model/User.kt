package com.lexwilliam.domain.model

data class User(
    val uid: String,
    val name: String?,
    val photoUrl: String?,
    val packs: List<PackInfo>?
)