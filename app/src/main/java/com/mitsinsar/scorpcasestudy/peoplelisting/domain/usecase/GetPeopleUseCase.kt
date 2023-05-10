package com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase

import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.Person

interface GetPeopleUseCase {

    suspend operator fun invoke(nextUrl: String?): Result<PaginatedResult<List<Person>>>
}
