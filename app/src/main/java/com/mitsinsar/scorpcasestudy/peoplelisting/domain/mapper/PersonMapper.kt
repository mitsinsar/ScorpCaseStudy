package com.mitsinsar.scorpcasestudy.peoplelisting.domain.mapper

import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.Person
import javax.inject.Inject

class PersonMapper @Inject constructor() {

    fun map(id: Int, fullName: String): Person {
        return Person(
            id = id,
            fullName = fullName
        )
    }
}
