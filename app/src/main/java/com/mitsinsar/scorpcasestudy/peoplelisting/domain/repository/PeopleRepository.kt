package com.mitsinsar.scorpcasestudy.peoplelisting.domain.repository

import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.PersonDto

interface PeopleRepository {

    suspend fun fetchPeople(nextUrl: String?): Result<PaginatedResult<List<PersonDto>>>

    companion object {
        const val INJECTION_NAME = "peopleRepositoryInjectionName"
    }
}
