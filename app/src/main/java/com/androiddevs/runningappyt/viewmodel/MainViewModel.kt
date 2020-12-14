package com.androiddevs.runningappyt.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.runningappyt.database.Run
import com.androiddevs.runningappyt.repository.MainRepository
import com.androiddevs.runningappyt.utils.SortType
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val runSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val runSortedByCaloriesBurned = mainRepository.getAllRunsSortedByCaloriesBurned()
    private val runSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis()
    private val runSortedByAverageSpeed = mainRepository.getAllRunsSortedByAvgSpeed()

    val runs = MediatorLiveData<List<Run>>()
    var sortType = SortType.DATE

    init {
        runs.addSource(runSortedByDate) {
            if (sortType == SortType.DATE) {
                it?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByAverageSpeed) {
            if (sortType == SortType.AVERAGE_SPEED) {
                it?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByCaloriesBurned) {
            if (sortType == SortType.CALORIES_BURNED) {
                it?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByDistance) {
            if (sortType == SortType.DISTANCE) {
                it?.let { runs.value = it }
            }
        }
        runs.addSource(runSortedByTimeInMillis) {
            if (sortType == SortType.RUNNING_TIME) {
                it?.let { runs.value = it }
            }
        }
    }

    fun sortRun(sortType: SortType) = when (sortType) {
        SortType.DATE -> runSortedByDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.DISTANCE -> runSortedByDistance.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> runSortedByCaloriesBurned.value?.let { runs.value = it }
        SortType.AVERAGE_SPEED -> runSortedByAverageSpeed.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}