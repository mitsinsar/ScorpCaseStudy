package com.mitsinsar.scorpcasestudy.peoplelisting.data.di

import com.mitsinsar.scorpcasestudy.core.utils.pagination.mapper.PaginatedResultMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.data.mapper.PersonDtoMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.data.repository.PeopleRepositoryImpl
import com.mitsinsar.scorpcasestudy.peoplelisting.data.source.PeopleDataSource
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.repository.PeopleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object PeopleRepositoryModule {

    @Provides
    @Named(PeopleRepository.INJECTION_NAME)
    fun providePeopleRepository(
        peopleDataSource: PeopleDataSource,
        paginatedResultMapper: PaginatedResultMapper,
        personDtoMapper: PersonDtoMapper
    ): PeopleRepository {
        return PeopleRepositoryImpl(
            peopleDataSource = peopleDataSource,
            paginatedResultMapper = paginatedResultMapper,
            personDtoMapper = personDtoMapper
        )
    }
}
