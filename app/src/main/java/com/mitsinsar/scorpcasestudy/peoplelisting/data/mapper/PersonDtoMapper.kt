package com.mitsinsar.scorpcasestudy.peoplelisting.data.mapper

import com.mitsinsar.scorpcasestudy.peoplelisting.data.model.PersonResponse
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.PersonDto
import javax.inject.Inject

class PersonDtoMapper @Inject constructor() {

    fun map(personResponse: PersonResponse): PersonDto {
        return PersonDto(
            id = personResponse.id,
            fullName = personResponse.fullName
        )
    }
}
