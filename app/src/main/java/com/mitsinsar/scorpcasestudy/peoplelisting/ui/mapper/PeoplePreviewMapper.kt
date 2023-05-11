package com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper

import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreview
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem
import javax.inject.Inject

class PeoplePreviewMapper @Inject constructor() {

    fun map(
        isLoadingVisible: Boolean = false,
        peopleItem: List<PersonItem> = emptyList(),
        nextPageUrl: String? = null
    ): PeoplePreview {
        return PeoplePreview(
            isLoadingVisible = isLoadingVisible,
            peopleItem = peopleItem,
            nextPageUrl = nextPageUrl
        )
    }
}
