package com.mitsinsar.scorpcasestudy.peoplelisting.ui.usecase

import com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase.GetPeopleUseCase
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper.PeoplePreviewMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper.PersonItemMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeoplePreviewUseCase @Inject constructor(
    private val getPeopleUseCase: GetPeopleUseCase,
    private val peoplePreviewMapper: PeoplePreviewMapper,
    private val personItemMapper: PersonItemMapper
) {

    fun getInitialPreview(): Flow<PeoplePreview> = flow {
        val initialPreview = peoplePreviewMapper.map(isLoadingVisible = true)
        emit(initialPreview)
        getPeopleUseCase(null)
            .onSuccess { paginatedResult ->
                val peopleItem = paginatedResult.data.map { person ->
                    personItemMapper.map(person.id.toString(), person.fullName)
                }
                val preview = peoplePreviewMapper.map(peopleItem = peopleItem, nextPageUrl = paginatedResult.nextUrl)
                emit(preview)
            }
            .onFailure {
                // TODO handle error case
            }
    }

    fun loadMorePeople(nextUrl: String?): Flow<PeoplePreview> = flow {
        // TODO load more people with next url
    }

    fun refreshPeopleList(): Flow<PeoplePreview> = flow {
        // TODO refresh people list
    }
}
