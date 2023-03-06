package com.nabilmh.seekmax.feature.home

import JobsQuery
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.nabilmh.seekmax.feature.home.model.Job
import com.nabilmh.seekmax.feature.home.model.SalaryRange
import com.nabilmh.seekmax.feature.home.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {
    val liveData = MutableLiveData<ViewState>()
    private var currentPage = 1
    private var canLoadMore = true

    private val resultsList = mutableListOf<Job>()

    fun getJobsList() {
        viewModelScope.launch {
            liveData.value = ViewState.Loading
            val response = mainRepository.getJobsList(currentPage)

            when {
                response.hasErrors() -> {
                    liveData.value = ViewState.Error(response.errors?.get(0)?.message!!)
                }
                else -> {
                    val jobs = mapToJob(response)
                    resultsList.addAll(jobs)
                    liveData.value = ViewState.Loaded(resultsList, false)

                }
            }
        }
    }

    fun loadMore() {
        if (canLoadMore) {
            viewModelScope.launch {
                currentPage++
                liveData.value = ViewState.Loaded(resultsList, true)
                val response = mainRepository.getJobsList(currentPage)

                when {
                    response.hasErrors() -> {
                        canLoadMore = false
                        liveData.value = ViewState.Loaded(resultsList, false)
                    }
                    else -> {
                        val jobs = mapToJob(response)

                        canLoadMore = jobs.isNotEmpty()
                        resultsList.addAll(jobs)

                        liveData.value = ViewState.Loaded(resultsList, false)
                    }
                }
            }
        }
    }

    private fun mapToJob(response: Response<JobsQuery.Data>): List<Job> {
        val jobs = response.data?.active()?.jobs()?.map {
            Job(
                it._id()!!,
                it.description()!!,
                it.haveIApplied()!!,
                it.industry()!!,
                it.location()!!,
                it.positionTitle()!!,
                SalaryRange(it.salaryRange()?.max()!!, it.salaryRange()?.min()!!)
            )
        } ?: emptyList()
        return jobs
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Loaded(val jobs: List<Job>, val loadingMore: Boolean) : ViewState()
        data class Error(val errorMessage: String) : ViewState()
    }
}