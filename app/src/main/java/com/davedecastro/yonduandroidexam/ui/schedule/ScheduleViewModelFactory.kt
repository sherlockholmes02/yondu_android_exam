package com.davedecastro.yonduandroidexam.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class ScheduleViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleViewModel(movieRepository) as T
    }
}