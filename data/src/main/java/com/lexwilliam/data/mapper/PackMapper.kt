package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.FlashcardResponse
import com.lexwilliam.data.model.PackInfoResponse
import com.lexwilliam.data.model.PackResponse
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.domain.model.PackInfo
import javax.inject.Inject

interface PackMapper {
    fun toResponse(pack: Pack): PackResponse
    fun toResponse(flashcard: Flashcard): FlashcardResponse
    fun toResponse(packInfo: PackInfo): PackInfoResponse
    fun toDomain(pack: PackResponse): Pack
    fun toDomain(flashcard: FlashcardResponse): Flashcard
    fun toDomain(packInfo: PackInfoResponse): PackInfo
}

class PackMapperImpl @Inject constructor(): PackMapper {
    override fun toResponse(pack: Pack): PackResponse =
        PackResponse(pack.id, pack.title, pack.pictureUrl, pack.creatorName, pack.creatorPhotoUrl, pack.flashcards?.map { toResponse(it) })

    override fun toResponse(flashcard: Flashcard): FlashcardResponse =
        FlashcardResponse(flashcard.id, flashcard.question, flashcard.answer)

    override fun toResponse(packInfo: PackInfo): PackInfoResponse =
        PackInfoResponse(packInfo.id, packInfo.title, packInfo.pictureUrl, packInfo.creatorName, packInfo.creatorPhotoUrl)

    override fun toDomain(pack: PackResponse): Pack =
        Pack(pack.id, pack.title, pack.pictureUrl, pack.creatorName, pack.creatorPhotoUrl, pack.flashcards?.map { toDomain(it) })

    override fun toDomain(flashcard: FlashcardResponse): Flashcard =
        Flashcard(flashcard.id, flashcard.question, flashcard.answer)

    override fun toDomain(packInfo: PackInfoResponse): PackInfo =
        PackInfo(packInfo.id, packInfo.title, packInfo.pictureUrl, packInfo.creatorName, packInfo.creatorPhotoUrl)

}