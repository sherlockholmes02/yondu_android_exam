package com.davedecastro.yonduandroidexam.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davedecastro.yonduandroidexam.R
import com.davedecastro.yonduandroidexam.data.db.YonduExamDatabase
import com.davedecastro.yonduandroidexam.data.network.MovieService
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository
import com.davedecastro.yonduandroidexam.databinding.ActivityMovieDetailsBinding
import com.davedecastro.yonduandroidexam.utils.Coroutines
import com.davedecastro.yonduandroidexam.utils.RetrofitSingleton
import com.davedecastro.yonduandroidexam.utils.into
import com.davedecastro.yonduandroidexam.utils.isServerReachable

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsInterface {
    lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        val yonduExamDatabase = YonduExamDatabase.getInstance()
        val movieService = RetrofitSingleton.get<MovieService>()
        val userRepository = MovieRepository(yonduExamDatabase, movieService)
        val factory = MovieDetailsViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory).get(MovieDetailsViewModel::class.java)
        viewModel.movieDetailsInterface = this
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        Coroutines.inputOutput {
            if (isServerReachable()) {
                bindUI()
            } else {
                Coroutines.main {
                    val movie = viewModel.movieCache.await()
                    movie.observe(this, Observer {
                        if (it == null) {
                            binding.pbMovieLoader.visibility = View.VISIBLE
                            binding.rlMovieLayout.visibility = View.GONE
                        } else {
                            binding.pbMovieLoader.visibility = View.GONE
                            binding.rlMovieLayout.visibility = View.VISIBLE
                            binding.movie = it
                            binding.releaseDate = it.readableReleaseDate
                            binding.duration = it.duration
                            binding.casts = it.casts
                            it.poster.into(binding.ivMovieImage)
                            it.posterLandscape.into(binding.ivMovieImageLandscape)
                        }
                    })
                }
            }
        }
    }

    private fun bindUI() = Coroutines.main {
        val movie = viewModel.movie.await()
        movie.observe(this, Observer {
            binding.pbMovieLoader.visibility = View.GONE
            binding.rlMovieLayout.visibility = View.VISIBLE
            binding.movie = it
            binding.releaseDate = it.readableReleaseDate
            binding.duration = it.duration
            binding.casts = it.casts
            it.poster.into(binding.ivMovieImage)
            it.posterLandscape.into(binding.ivMovieImageLandscape)
        })
    }

    override fun onFetchStarted() {
        binding.pbMovieLoader.visibility = View.VISIBLE
    }
}