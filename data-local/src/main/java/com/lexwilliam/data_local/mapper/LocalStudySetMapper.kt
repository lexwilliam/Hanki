package com.lexwilliam.data_local.mapper

import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.data_local.model.StudySetEntity
import com.lexwilliam.data_local.model.StudySetWithFlashcard
import javax.inject.Inject

interface LocalStudySetMapper {
    fun toRepo(studySet: StudySetWithFlashcard): StudySetRepositoryModel
    fun toRepo(studySetList: List<StudySetWithFlashcard>): List<StudySetRepositoryModel>
    fun toEntity(studySet: StudySetRepositoryModel): StudySetEntity
    fun toEntity(studySetList: List<StudySetRepositoryModel>): List<StudySetEntity>
}

class LocalStudySetMapperImpl @Inject constructor(
    private val localFlashcardMapper: LocalFlashcardMapper
): LocalStudySetMapper {
    override fun toRepo(studySet: StudySetWithFlashcard): StudySetRepositoryModel =
        StudySetRepositoryModel(
            id = studySet.studySet.id,
            name = studySet.studySet.name,
            totalFlashcard = studySet.studySet.totalFlashcard,
            flashcards = localFlashcardMapper.toRepo(studySet.flashcards)
        )


    override fun toRepo(studySetList: List<StudySetWithFlashcard>): List<StudySetRepositoryModel> =
        studySetList.map { toRepo(it) }


    override fun toEntity(studySet: StudySetRepositoryModel): StudySetEntity =
        StudySetEntity(
            id = studySet.id,
            name = studySet.name,
            totalFlashcard = studySet.totalFlashcard
        )

    override fun toEntity(studySetList: List<StudySetRepositoryModel>): List<StudySetEntity> =
        studySetList.map { toEntity(it) }

}