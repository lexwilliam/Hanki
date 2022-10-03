package com.lexwilliam.domain.model

import android.net.Uri

data class User(
    val uid: String,
    val name: String?,
    val photoUrl: Uri?
)