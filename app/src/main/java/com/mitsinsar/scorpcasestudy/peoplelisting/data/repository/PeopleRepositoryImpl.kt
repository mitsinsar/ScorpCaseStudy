package com.mitsinsar.scorpcasestudy.peoplelisting.data.repository

import com.mitsinsar.scorpcasestudy.core.utils.pagination.mapper.PaginatedResultMapper
import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import com.mitsinsar.scorpcasestudy.peoplelisting.data.mapper.PersonDtoMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.data.model.FetchError
import com.mitsinsar.scorpcasestudy.peoplelisting.data.model.FetchResponse
import com.mitsinsar.scorpcasestudy.peoplelisting.data.source.PeopleDataSource
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.PersonDto
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.repository.PeopleRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PeopleRepositoryImpl @Inject constructor(
    private val peopleDataSource: PeopleDataSource,
    private val paginatedResultMapper: PaginatedResultMapper,
    private val personDtoMapper: PersonDtoMapper
) : PeopleRepository {

    override suspend fun fetchPeople(
        nextUrl: String?
    ): Result<PaginatedResult<List<PersonDto>>> = suspendCoroutine { continuation ->
        peopleDataSource.fetch(nextUrl) { response: FetchResponse?, error: FetchError? ->
            val result = when {
                error != null -> Result.failure(error.throwable)
                response != null -> mapFetchResponseToPaginatedResult(response)
                else -> Result.failure(IOException("Server error!"))
            }
            continuation.resume(result)
        }
    }

    private fun mapFetchResponseToPaginatedResult(
        fetchResponse: FetchResponse
    ): Result<PaginatedResult<List<PersonDto>>> {
        val personDtoList = fetchResponse.people.map { person ->
            personDtoMapper.map(person)
        }
        val paginatedResult = paginatedResultMapper.map(personDtoList, fetchResponse.next)
        return Result.success(paginatedResult)
    }
}
