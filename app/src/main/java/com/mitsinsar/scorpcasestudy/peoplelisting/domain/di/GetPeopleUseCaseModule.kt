package com.mitsinsar.scorpcasestudy.peoplelisting.domain.di

import com.mitsinsar.scorpcasestudy.peoplelisting.domain.mapper.PersonMapper
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.repository.PeopleRepository
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase.GetPeopleUseCase
import com.mitsinsar.scorpcasestudy.peoplelisting.domain.usecase.GetPeopleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object GetPeopleUseCaseModule {

    @Provides
    fun provideGetPeopleUseCase(
        @Named(PeopleRepository.INJECTION_NAME)
        peopleRepository: PeopleRepository,
        personMapper: PersonMapper
    ): GetPeopleUseCase {
        return GetPeopleUseCaseImpl(
            peopleRepository = peopleRepository,
            personMapper = personMapper
        )
    }
}
