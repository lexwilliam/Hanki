package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.UserResponse
import com.lexwilliam.domain.model.User
import javax.inject.Inject

interface UserMapper {
    fun toResponse(user: User): UserResponse
    fun toDomain(user: UserResponse): User
}

class UserMapperImpl @Inject constructor(
    private val packMapper: PackMapper
): UserMapper {
    override fun toResponse(user: User): UserResponse =
        UserResponse(user.uid, user.name, user.photoUrl, user.packs?.map { packMapper.toResponse(it) })

    override fun toDomain(user: UserResponse): User =
        User(user.uid?:"", user.name, user.photoUrl, user.packs?.map { packMapper.toDomain(it) })

}
