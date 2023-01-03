package com.lexwilliam.feature_add.mapper

import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.domain.model.Pack
import com.lexwilliam.feature_add.model.FlashcardPresentation
import com.lexwilliam.feature_add.model.PackPresentation
import javax.inject.Inject

interface PackMapper {
    fun toDomain(pack: PackPresentation): Pack
    fun toDomain(flashcard: FlashcardPresentation): Flashcard
}

class PackMapperImpl @Inject constructor(): PackMapper {
    override fun toDomain(pack: PackPresentation): Pack =
        Pack(pack.id, pack.name, pack.creatorName, pack.creatorPhotoUrl, pack.flashcards.map { toDomain(it) })

    override fun toDomain(flashcard: FlashcardPresentation): Flashcard =
        Flashcard(flashcard.id, flashcard.question, flashcard.answer)

}