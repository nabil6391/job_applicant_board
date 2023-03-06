package com.nabilmh.seekmax.feature.home.repository

import JobsQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.nabilmh.seekmax.feature.home.api.MainApiService
import com.nabilmh.seekmax.utils.graphApiCall
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService, private val graphqlClient: ApolloClient
) {
    suspend fun getJobsList(page: Int): Response<JobsQuery.Data> {
        return graphApiCall { graphqlClient.query(JobsQuery(Input.optional(page))) }
    }
}
