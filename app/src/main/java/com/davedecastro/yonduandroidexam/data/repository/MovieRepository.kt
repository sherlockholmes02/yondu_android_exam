package com.davedecastro.yonduandroidexam.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davedecastro.yonduandroidexam.data.db.YonduExamDatabase
import com.davedecastro.yonduandroidexam.data.db.entities.Movie
import com.davedecastro.yonduandroidexam.data.db.entities.Schedule
import com.davedecastro.yonduandroidexam.data.db.entities.Seatmap
import com.davedecastro.yonduandroidexam.data.network.MovieService
import com.davedecastro.yonduandroidexam.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val yonduExamDatabase: YonduExamDatabase?,
    private val movieService: MovieService
) {

    private val movie = MutableLiveData<Movie>()
    private val schedule = MutableLiveData<Schedule>()
    private val seatmap = MutableLiveData<Seatmap>()

    init {
        movie.observeForever {
            saveMovie(it)
        }
    }

    suspend fun getMovie(): LiveData<Movie> {
        return withContext(Dispatchers.IO) {
            fetchMovie()
            yonduExamDatabase?.movieDao()!!.get()
        }
    }

    suspend fun getMovieCache(): LiveData<Movie> {
        return withContext(Dispatchers.IO) {
            yonduExamDatabase?.movieDao()!!.get()
        }
    }

    private suspend fun fetchMovie() {
        val response = movieService.getMovieDetail()
        movie.postValue(response.body())
    }

    private fun saveMovie(movie: Movie) {
        Coroutines.inputOutput {
            yonduExamDatabase?.movieDao()!!.insert(movie)
        }
    }

    suspend fun fetchSchedule(): LiveData<Schedule> {
        val response = movieService.getSchedule()
        schedule.postValue(response.body())
        return schedule
    }

    suspend fun fetchSeatMap(): LiveData<Seatmap> {
        val response = movieService.getSeatmap()
        seatmap.postValue(response.body())
        return seatmap
    }
}