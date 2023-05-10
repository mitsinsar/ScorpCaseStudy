package com.mitsinsar.scorpcasestudy.core.utils.pagination.mapper

import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult
import javax.inject.Inject

class PaginatedResultMapper @Inject constructor() {

    fun <T> map(data: T, nextUrl: String?): PaginatedResult<T> {
        return PaginatedResult(data, nextUrl)
    }
}
