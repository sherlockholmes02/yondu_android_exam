package com.davedecastro.yonduandroidexam.data.network

import com.davedecastro.yonduandroidexam.data.db.entities.Movie
import com.davedecastro.yonduandroidexam.data.db.entities.Schedule
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("movie.json")
    suspend fun getMovieDetail(): Response<Movie>

    @GET("schedule.json")
    suspend fun getSchedule(): Response<Schedule>

}