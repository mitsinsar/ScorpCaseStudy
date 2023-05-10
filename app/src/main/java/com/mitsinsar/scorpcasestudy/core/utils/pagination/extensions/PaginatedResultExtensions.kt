package com.mitsinsar.scorpcasestudy.core.utils.pagination.extensions

import com.mitsinsar.scorpcasestudy.core.utils.pagination.model.PaginatedResult

fun <IN, OUT> PaginatedResult<IN>.map(transform: (value: IN) -> OUT): PaginatedResult<OUT> {
    return PaginatedResult<OUT>(transform(data), nextUrl)
}
