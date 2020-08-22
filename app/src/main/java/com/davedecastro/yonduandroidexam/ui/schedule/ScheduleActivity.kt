package com.davedecastro.yonduandroidexam.ui.schedule

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davedecastro.yonduandroidexam.R
import com.davedecastro.yonduandroidexam.data.db.entities.MovieDate
import com.davedecastro.yonduandroidexam.data.db.entities.Schedule
import com.davedecastro.yonduandroidexam.data.network.MovieService
import com.davedecastro.yonduandroidexam.data.repository.MovieRepository
import com.davedecastro.yonduandroidexam.databinding.ActivityScheduleBinding
import com.davedecastro.yonduandroidexam.ui.schedule.adapters.DateArrayAdapter
import com.davedecastro.yonduandroidexam.utils.Coroutines
import com.davedecastro.yonduandroidexam.utils.RetrofitSingleton

class ScheduleActivity : AppCompatActivity() {
    lateinit var binding: ActivityScheduleBinding
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var schedule: Schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule)
        val movieService = RetrofitSingleton.get<MovieService>()
        val userRepository = MovieRepository(null, movieService)
        val factory = ScheduleViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory).get(ScheduleViewModel::class.java)
        bindUI()
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindUI() {
        Coroutines.main {
            binding.theater = intent.getStringExtra("theater")
            val scheduleObserver = viewModel.schedule.await()
            scheduleObserver.observe(this, Observer {
                schedule = it
                setSpinnerDates()
                setSpinnerCinemas()
                setSpinnerTime()
            })
        }
    }

    private fun setSpinnerTime() {
        if (binding.spnScheduleCinema.selectedItem != null) {
            val movieDate = binding.spnScheduleDate.selectedItem as MovieDate
            val cinema = binding.spnScheduleCinema.selectedItem.toString()
            val parent = movieDate.id + "-" + cinema
                .substringAfter("Cinema ")

            val labels = schedule.times.filter { it.parent == parent }
                .map { it -> it.times.map { it.label } }.toString().replace("[[", "")
                .replace("]]", "").split(", ")

            lateinit var arrayAdapter: ArrayAdapter<String>
            if (labels.size != 1) {
                arrayAdapter =
                    ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item, labels
                    )
                arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
            } else {
                arrayAdapter = getEmptyAdapter()
            }
            binding.spnScheduleTime.adapter = arrayAdapter
        } else {
            binding.spnScheduleTime.adapter = getEmptyAdapter()
        }
    }

    private fun getEmptyAdapter(): ArrayAdapter<String> {
        val emptyList = listOf<String>()
        var arrayAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, emptyList
            )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        return arrayAdapter
    }

    private fun setSpinnerCinemas() {
        val movieDate = binding.spnScheduleDate.selectedItem as MovieDate
        val labels = schedule.cinemas.filter { it.parent == movieDate.id }
            .map { it -> it.cinemasNumbers.map { it.label } }.toString().replace("[[", "")
            .replace("]]", "").split(", ")
        lateinit var arrayAdapter: ArrayAdapter<String>
        if (labels.size != 1) {
            arrayAdapter =
                ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, labels
                )
            arrayAdapter.setDropDownViewResource(R.layout.spinner_item)
        } else {
            arrayAdapter = getEmptyAdapter()
        }
        binding.spnScheduleCinema.apply {
            adapter = arrayAdapter

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setSpinnerTime()
                }
            }
        }
    }

    private fun setSpinnerDates() {
        var dateArrayAdapter = DateArrayAdapter(this, schedule.dates)
        dateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spnScheduleDate.apply {
            adapter = dateArrayAdapter

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setSpinnerCinemas()
                    setSpinnerTime()
                }
            }
        }
    }
}
