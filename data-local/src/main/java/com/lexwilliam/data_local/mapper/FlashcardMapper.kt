package com.lexwilliam.data_local.mapper

import com.lexwilliam.data.model.FlashcardRepositoryModel
import com.lexwilliam.data_local.model.StudySetWithFlashcard
import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.data_local.model.FlashcardEntity
import com.lexwilliam.data_local.model.StudySetEntity
import javax.inject.Inject

interface FlashcardMapper {
    fun toRepo(flashcard: FlashcardEntity): FlashcardRepositoryModel
    fun toRepo(flashcardList: List<FlashcardEntity>): List<FlashcardRepositoryModel>
    fun toEntity(flashcard: FlashcardRepositoryModel): FlashcardEntity
    fun toEntity(flashcardList: List<FlashcardRepositoryModel>): List<FlashcardEntity>
}


class FlashcardMapperImpl @Inject constructor():
    FlashcardMapper {

    override fun toRepo(flashcard: FlashcardEntity): FlashcardRepositoryModel =
         FlashcardRepositoryModel(
            id = flashcard.id,
            studySetName =  flashcard.studySetName,
            question = flashcard.question,
            answer = flashcard.answer
        )

    override fun toRepo(flashcardList: List<FlashcardEntity>): List<FlashcardRepositoryModel> =
        flashcardList.map{ toRepo(it) }

    override fun toEntity(flashcard: FlashcardRepositoryModel): FlashcardEntity =
        FlashcardEntity(
            id = flashcard.id,
            studySetName = flashcard.studySetName,
            question = flashcard.question,
            answer = flashcard.answer
        )

    override fun toEntity(flashcardList: List<FlashcardRepositoryModel>): List<FlashcardEntity> =
        flashcardList.map { toEntity(it) }

}