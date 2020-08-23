package com.davedecastro.yonduandroidexam.ui.schedule

import androidx.lifecycle.ViewModel
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository
import com.davedecastro.yonduandroidexam.utils.lazyDeferred

class ScheduleViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var scheduleInterface: ScheduleInterface? = null

    val schedule by lazyDeferred {
        scheduleInterface?.onFetchStarted()
        movieRepository.fetchSchedule()
    }

    val seatmap by lazyDeferred {
        movieRepository.fetchSeatMap()
    }

}