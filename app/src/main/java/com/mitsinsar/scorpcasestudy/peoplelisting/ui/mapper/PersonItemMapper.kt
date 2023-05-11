package com.mitsinsar.scorpcasestudy.peoplelisting.ui.mapper

import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem
import javax.inject.Inject

class PersonItemMapper @Inject constructor() {

    fun map(id: String, fullName: String): PersonItem {
        return PersonItem(
            id = id,
            fullName = fullName
        )
    }
}
