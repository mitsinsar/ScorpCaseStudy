package com.mitsinsar.scorpcasestudy.peoplelisting.ui.usecase

import com.mitsinsar.scorpcasestudy.core.utils.exceptions.NoUserFoundException
import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.Person
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase.GetPeopleUseCase
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper.PeoplePreviewMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper.PersonItemMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreview
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreviewInfoBarAction.GenericErrorBar
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreviewInfoBarAction.NoUserFoundInfoBar
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreviewInfoBarAction.ServerErrorBar
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetUpdatedPeoplePreviewWithPeopleListUseCase @Inject constructor(
    private val getPeopleUseCase: GetPeopleUseCase,
    private val personItemMapper: PersonItemMapper,
    private val peoplePreviewMapper: PeoplePreviewMapper
) {

    suspend operator fun invoke(previousPreview: PeoplePreview, nextUrl: String?) = flow {
        emit(previousPreview.copy(isLoadingVisible = true))
        getPeopleUseCase(nextUrl)
            .onSuccess { paginatedResult ->
                emit(getSuccessPreview(previousPreview, paginatedResult))
            }
            .onFailure { throwable ->
                emit(getErrorPreview(previousPreview, throwable))
            }
    }

    private fun getSuccessPreview(
        previousPreview: PeoplePreview,
        paginatedResult: PaginatedResult<List<Person>>
    ): PeoplePreview {
        val updatePeopleItem = previousPreview.peopleItem + paginatedResult.data.map { person ->
            personItemMapper.map(person.id.toString(), person.fullName)
        }
        return peoplePreviewMapper.map(
            peopleItem = updatePeopleItem,
            nextPageUrl = paginatedResult.nextUrl,
            isLoadingVisible = false
        )
    }

    private fun getErrorPreview(previousPreview: PeoplePreview, throwable: Throwable): PeoplePreview {
        val infoBarAction = when (throwable) {
            is NoUserFoundException -> NoUserFoundInfoBar
            is IOException -> ServerErrorBar
            else -> GenericErrorBar
        }
        return previousPreview.copy(infoBarAction = infoBarAction, isLoadingVisible = false)
    }
}
