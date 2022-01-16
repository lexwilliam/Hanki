package com.lexwilliam.data.mapper

import com.lexwilliam.data.model.StudySetRepositoryModel
import com.lexwilliam.domain.model.StudySet
import javax.inject.Inject

interface RepositoryStudySetMapper {
    fun toDomain(studySet: StudySetRepositoryModel): StudySet
    fun toDomain(studySetList: List<StudySetRepositoryModel>): List<StudySet>
    fun toRepo(studySet: StudySet): StudySetRepositoryModel
    fun toRepo(studySetList: List<StudySet>): List<StudySetRepositoryModel>
}

class RepositoryStudySetMapperImpl @Inject constructor(
    private val repositoryFlashcardMapper: RepositoryFlashcardMapper
): RepositoryStudySetMapper {

    override fun toDomain(studySet: StudySetRepositoryModel): StudySet =
        StudySet(
            id = studySet.id,
            name = studySet.name,
            totalFlashcard = studySet.totalFlashcard,
            flashcards = repositoryFlashcardMapper.toDomain(studySet.flashcards)
        )

    override fun toDomain(studySetList: List<StudySetRepositoryModel>): List<StudySet> =
        studySetList.map { toDomain(it) }

    override fun toRepo(studySet: StudySet): StudySetRepositoryModel =
        StudySetRepositoryModel(
            id = studySet.id,
            name = studySet.name,
            totalFlashcard = studySet.totalFlashcard,
            flashcards = repositoryFlashcardMapper.toRepo(studySet.flashcards)
        )


    override fun toRepo(studySetList: List<StudySet>): List<StudySetRepositoryModel> =
        studySetList.map { toRepo(it) }

}