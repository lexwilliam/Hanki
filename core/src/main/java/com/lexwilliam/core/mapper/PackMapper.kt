package com.lexwilliam.core.mapper

import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.core.model.PackInfoPresentation
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.domain.model.PackInfo
import javax.inject.Inject

interface PackMapper {
    fun toDomain(pack: PackPresentation): Pack
    fun toDomain(flashcard: FlashcardPresentation): Flashcard
    fun toDomain(packInfo: PackInfoPresentation): PackInfo
    fun toPresentation(pack: Pack): PackPresentation
    fun toPresentation(flashcard: Flashcard): FlashcardPresentation
    fun toPresentation(packInfo: PackInfo): PackInfoPresentation
}

class PackMapperImpl @Inject constructor(): PackMapper {

    override fun toDomain(pack: PackPresentation): Pack =
        Pack(pack.id, pack.title, pack.creatorName, pack.creatorPhotoUrl, pack.flashcards.map { toDomain(it) })

    override fun toDomain(flashcard: FlashcardPresentation): Flashcard =
        Flashcard(flashcard.id, flashcard.question, flashcard.answer)

    override fun toDomain(packInfo: PackInfoPresentation): PackInfo =
        PackInfo(packInfo.id, packInfo.title, packInfo.creatorName, packInfo.creatorPhotoUrl)

    override fun toPresentation(pack: Pack): PackPresentation =
        PackPresentation(pack.id, pack.title, pack.creatorName, pack.creatorPhotoUrl, pack.flashcards.map { toPresentation(it) })

    override fun toPresentation(flashcard: Flashcard): FlashcardPresentation =
        FlashcardPresentation(flashcard.id, flashcard.question, flashcard.answer)

    override fun toPresentation(packInfo: PackInfo): PackInfoPresentation =
        PackInfoPresentation(packInfo.id?:"", packInfo.title?:"", packInfo.creatorName?:"", packInfo.creatorPhotoUrl)

}