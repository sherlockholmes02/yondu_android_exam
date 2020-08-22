package com.davedecastro.yonduandroidexam.ui.details

import androidx.lifecycle.ViewModel
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository
import com.davedecastro.yonduandroidexam.utils.lazyDeferred

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    var movieDetailsInterface: MovieDetailsInterface? = null

    val movie by lazyDeferred {
        movieDetailsInterface?.onFetchStarted()
        movieRepository.getMovie()
    }

    val movieCache by lazyDeferred {
        movieRepository.getMovieCache()
    }

}