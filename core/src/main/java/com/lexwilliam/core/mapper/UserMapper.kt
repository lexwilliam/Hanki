package com.lexwilliam.core.mapper

import com.lexwilliam.core.model.PackInfoPresentation
import com.lexwilliam.core.model.UserPresentation
import com.lexwilliam.domain.model.User
import javax.inject.Inject

interface UserMapper {
    fun toDomain(user: UserPresentation): User
    fun toPresentation(user: User): UserPresentation
}

class UserMapperImpl @Inject constructor(
    private val packMapper: PackMapper
): UserMapper {
    override fun toDomain(user: UserPresentation): User =
        User(user.uid, user.name, user.photoUrl, user.packs.map { packMapper.toDomain(it) })

    override fun toPresentation(user: User): UserPresentation =
        UserPresentation(user.uid, user.name?:"", user.photoUrl?:"", user.packs?.map { packMapper.toPresentation(it) } ?: emptyList() )

}
