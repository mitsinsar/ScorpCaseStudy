package com.mitsinsar.scorpcasestudy.core.utils.pagination.model

data class PaginatedResult<T>(val data: T, val nextUrl: String?)
