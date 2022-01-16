package com.lexwilliam.hanki.presentation.viewmodel.mapper

import com.lexwilliam.domain.model.StudySet
import com.lexwilliam.hanki.model.StudySetPresentation
import javax.inject.Inject

interface StudySetMapperPresentation {
    fun toDomain(studySet: StudySetPresentation): StudySet
    fun toDomain(studySetList: List<StudySetPresentation>): List<StudySet>
    fun toPresentation(studySet: StudySet): StudySetPresentation
    fun toPresentation(studySetList: List<StudySet>): List<StudySetPresentation>
}

class StudySetMapperPresentationImpl @Inject constructor(
    private val flashcardMapperPresentation: FlashcardMapperPresentation
): StudySetMapperPresentation {

    override fun toDomain(studySet: StudySetPresentation): StudySet =
        StudySet(
            id = studySet.id,
            name = studySet.name,
            totalFlashcard = studySet.totalFlashcard,
            flashcards = flashcardMapperPresentation.toDomain(studySet.flashcards)
        )

    override fun toDomain(studySetList: List<StudySetPresentation>): List<StudySet> =
        studySetList.map { toDomain(it) }

    override fun toPresentation(studySet: StudySet): StudySetPresentation =
        StudySetPresentation(
            id = studySet.id,
            name = studySet.name,
            totalFlashcard = studySet.totalFlashcard,
            flashcards = flashcardMapperPresentation.toPresentation(studySet.flashcards)
        )


    override fun toPresentation(studySetList: List<StudySet>): List<StudySetPresentation> =
        studySetList.map { toPresentation(it) }

}