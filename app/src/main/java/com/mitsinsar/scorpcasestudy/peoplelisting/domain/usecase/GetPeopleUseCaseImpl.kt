package com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase

import com.mitsinsar.scorpcasestudy.core.utils.exceptions.NoUserFoundException
import com.mitsinsar.scorpcasestudy.core.utils.exceptions.ScorpIOException
import com.mitsinsar.scorpcasestudy.core.utils.pagination.extensions.map
import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.mapper.PersonMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.Person
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.PersonDto
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.repository.PeopleRepository
import javax.inject.Named

class GetPeopleUseCaseImpl(
    @Named(PeopleRepository.INJECTION_NAME)
    private val peopleRepository: PeopleRepository,
    private val personMapper: PersonMapper
) : GetPeopleUseCase {

    override suspend fun invoke(nextUrl: String?): Result<PaginatedResult<List<Person>>> {
        return with(peopleRepository.fetchPeople(nextUrl)) {
            if (isSuccess) {
                val paginatedResultList = getOrNull()
                if (paginatedResultList == null || paginatedResultList.data.isEmpty()) {
                    mapFailureResult(NoUserFoundException())
                } else {
                    mapSuccessResult(this)
                }
            } else {
                mapFailureResult(exceptionOrNull() ?: ScorpIOException())
            }
        }
    }

    private fun mapSuccessResult(
        result: Result<PaginatedResult<List<PersonDto>>>
    ): Result<PaginatedResult<List<Person>>> {
        return result.map { paginatedResult ->
            paginatedResult.map { personDtoList ->
                personDtoList.mapNotNull { personDto ->
                    if (personDto.id != null && personDto.fullName != null) {
                        personMapper.map(personDto.id, personDto.fullName)
                    } else {
                        null
                    }
                }
            }
        }
    }

    private fun mapFailureResult(throwable: Throwable): Result<PaginatedResult<List<Person>>> {
        return Result.failure(throwable)
    }
}
