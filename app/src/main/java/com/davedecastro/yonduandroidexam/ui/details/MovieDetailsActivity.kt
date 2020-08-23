package com.davedecastro.yonduandroidexam.ui.details

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davedecastro.yonduandroidexam.R
import com.davedecastro.yonduandroidexam.data.db.YonduExamDatabase
import com.davedecastro.yonduandroidexam.data.db.entities.Movie
import com.davedecastro.yonduandroidexam.data.network.MovieService
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository
import com.davedecastro.yonduandroidexam.databinding.ActivityMovieDetailsBinding
import com.davedecastro.yonduandroidexam.ui.schedule.ScheduleActivity
import com.davedecastro.yonduandroidexam.utils.Coroutines
import com.davedecastro.yonduandroidexam.utils.RetrofitSingleton
import com.davedecastro.yonduandroidexam.utils.into
import com.davedecastro.yonduandroidexam.utils.isServerReachable
import com.google.android.material.snackbar.Snackbar

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsInterface {
    lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel
    private var movie: Movie? = null

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
                    val movieObserver = viewModel.movieCache.await()
                    movieObserver.observe(this, Observer {
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
        val movieObserver = viewModel.movie.await()
        movieObserver.observe(this, Observer {
            movie = it
            binding.pbMovieLoader.visibility = View.GONE
            binding.rlMovieLayout.visibility = View.VISIBLE
            binding.movie = it
            if (it != null) {
                binding.releaseDate = it.readableReleaseDate
                binding.duration = it.duration
                binding.casts = it.casts
                it.poster.into(binding.ivMovieImage)
                it.posterLandscape.into(binding.ivMovieImageLandscape)
            }
        })

        binding.btnMovieView.clickWithDebounce {
            onViewSeatMap()
        }
    }

    override fun onFetchStarted() {
        binding.pbMovieLoader.visibility = View.VISIBLE
    }

    fun onViewSeatMap() {
        Coroutines.inputOutput {
            if (isServerReachable()) {
                val intent = Intent(this, ScheduleActivity::class.java)
                intent.putExtra("theater", movie?.theater)
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.lbl_check_internet_connect,
                    Snackbar.LENGTH_LONG
                ).apply {
                    setBackgroundTint(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.error
                        )
                    )
                    setActionTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.white
                        )
                    )
                    setAction(R.string.lbl_close) { dismiss() }
                }.show()
            }
        }
    }

    fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0

            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()

                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }
}