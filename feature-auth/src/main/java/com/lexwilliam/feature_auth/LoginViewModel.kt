package com.lexwilliam.feature_auth

import com.lexwilliam.domain.usecase.InsertUser
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val insertUser: InsertUser
) {

}