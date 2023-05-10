package com.mitsinsar.scorpcasestudy.peoplelisting.data.model

// I've changed Person as PersonResponse since it is a response class that comes from data source
// I've also made params nullable.
data class PersonResponse(val id: Int?, val fullName: String?)
