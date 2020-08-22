package com.davedecastro.yonduandroidexam.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieDetailsViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieRepository) as T
    }
}