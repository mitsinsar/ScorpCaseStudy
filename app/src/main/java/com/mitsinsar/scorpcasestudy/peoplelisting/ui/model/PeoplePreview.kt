package com.mitsinsar.scorpcasestudy.peoplelisting.ui.model

data class PeoplePreview(
    val isLoadingVisible: Boolean,
    val peopleItem: List<PersonItem>,
    val nextPageUrl: String?
)
