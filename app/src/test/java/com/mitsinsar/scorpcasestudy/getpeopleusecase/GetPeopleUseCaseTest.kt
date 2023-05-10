package com.mitsinsar.scorpcasestudy.getpeopleusecase

import com.mitsinsar.scorpcasestudy.core.utils.exceptions.NoUserFoundException
import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.mapper.PersonMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.Person
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.model.PersonDto
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.repository.PeopleRepository
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase.GetPeopleUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPeopleUseCaseTest {

    @Test
    fun `ensure empty list returns NoUserFoundException`() = runTest {
        val mockPeopleRepository = object : PeopleRepository {
            override suspend fun fetchPeople(nextUrl: String?): Result<PaginatedResult<List<PersonDto>>> {
                return Result.success(PaginatedResult(emptyList(), null))
            }
        }
        val getPeopleUseCase = GetPeopleUseCaseImpl(mockPeopleRepository, PersonMapper())

        val result = getPeopleUseCase(null)
        val exceptedResult = Result.failure<PaginatedResult<List<Person>>>(NoUserFoundException())

        assert(result.exceptionOrNull() == exceptedResult.exceptionOrNull())
    }

    @Test
    fun `ensure dtos are mapped correctly`() = runTest {
        val mockPersonDtoList = listOf(PersonDto(1, "Dummy Name"), PersonDto(2, "Dummy Name"))
        val mockPeopleRepository = object : PeopleRepository {
            override suspend fun fetchPeople(nextUrl: String?): Result<PaginatedResult<List<PersonDto>>> {
                return Result.success(PaginatedResult(mockPersonDtoList, null))
            }
        }
        val getPeopleUseCase = GetPeopleUseCaseImpl(mockPeopleRepository, PersonMapper())

        val result = getPeopleUseCase(null).getOrNull()?.data.orEmpty()

        val areItemsEqual = mockPersonDtoList.zip(result) { personDto, person ->
            personDto.id == person.id && personDto.fullName == person.fullName
        }.all { it }
        assert(areItemsEqual)
    }

    @Test
    fun `ensure people without id are filtered out`() = runTest {
        val safeId = 179
        val mockPersonDtoList = listOf(PersonDto(safeId, "Dummy Name"), PersonDto(null, "Dummy Name"))
        val mockPeopleRepository = object : PeopleRepository {
            override suspend fun fetchPeople(nextUrl: String?): Result<PaginatedResult<List<PersonDto>>> {
                return Result.success(PaginatedResult(mockPersonDtoList, null))
            }
        }
        val getPeopleUseCase = GetPeopleUseCaseImpl(mockPeopleRepository, PersonMapper())

        val result = getPeopleUseCase(null).getOrNull()?.data.orEmpty()

        assert(result.size == 1 && result.single().id == safeId)
    }
}
