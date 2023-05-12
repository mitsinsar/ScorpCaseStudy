package com.mitsinsar.scorpcasestudy.peoplelisting.ui.usecase

import com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper.PeoplePreviewMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeoplePreviewUseCase @Inject constructor(
    private val peoplePreviewMapper: PeoplePreviewMapper,
    private val getUpdatedPeoplePreviewWithPeopleListUseCase: GetUpdatedPeoplePreviewWithPeopleListUseCase
) {

    suspend fun getInitialPreview(): Flow<PeoplePreview> {
        return getUpdatedPeoplePreviewWithPeopleListUseCase(createInitialPreview(), nextUrl = null)
    }

    suspend fun loadMorePeople(previousPreview: PeoplePreview): Flow<PeoplePreview> {
        return getUpdatedPeoplePreviewWithPeopleListUseCase(previousPreview, nextUrl = previousPreview.nextPageUrl)
    }

    suspend fun loadMoreIfListedItemsAreSmallerThanScreen(
        isLastItemCompletelyVisible: Boolean,
        previousPreview: PeoplePreview
    ): Flow<PeoplePreview>? {
        return if (isLastItemCompletelyVisible) {
            getUpdatedPeoplePreviewWithPeopleListUseCase(previousPreview, previousPreview.nextPageUrl)
        } else null
    }

    suspend fun refreshPeopleList(previousPreview: PeoplePreview?): Flow<PeoplePreview> {
        val safeListClearedPreview = getSafePreview(previousPreview?.copy(peopleItem = emptyList()))
        return getUpdatedPeoplePreviewWithPeopleListUseCase(safeListClearedPreview, nextUrl = null)
    }

    suspend fun getUpdatedPreviewWithInfoAction(previousPreview: PeoplePreview): Flow<PeoplePreview> {
        return getUpdatedPeoplePreviewWithPeopleListUseCase(previousPreview, nextUrl = previousPreview.nextPageUrl)
//        Since every action refreshes the list, there is no reason to check info action
//        when (previousPreview.infoBarAction) {
//            GenericErrorBar -> TODO()
//            ServerErrorBar -> TODO()
//            NoUserFoundInfoBar -> TODO()
//        }
    }

    private fun getSafePreview(previousPreview: PeoplePreview?): PeoplePreview {
        return previousPreview.takeIf { it != null } ?: createInitialPreview()
    }

    private fun createInitialPreview() = peoplePreviewMapper.map()
}
