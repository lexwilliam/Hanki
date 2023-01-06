package com.lexwilliam.data.model

data class UserResponse(
    val uid: String? = null,
    val name: String? = null,
    val photoUrl: String? = null,
    val packs: List<PackInfoResponse>? = null
)