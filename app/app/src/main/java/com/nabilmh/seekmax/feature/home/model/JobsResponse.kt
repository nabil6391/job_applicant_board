package com.nabilmh.seekmax.feature.home.model


data class JobsResponse(
    val jobs: Jobs
)

data class Jobs(
    val jobs: List<Job>,
    val page: Int,
    val size: Int,
    val total: Int
)

data class Job(
    val _id: String,
    val description: String,
    val haveIApplied: Boolean,
    val industry: Int,
    val location: Int,
    val positionTitle: String,
    val salaryRange: SalaryRange
)

data class SalaryRange(
    val max: Int,
    val min: Int
)
