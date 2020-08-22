package com.davedecastro.yonduandroidexam.ui.schedule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.davedecastro.yonduandroidexam.R
import com.davedecastro.yonduandroidexam.data.db.entities.MovieDate
import com.davedecastro.yonduandroidexam.databinding.SpinnerItemBinding

class DateArrayAdapter(
    context: Context,
    movieDate: List<MovieDate>
) :
    ArrayAdapter<MovieDate>(context, 0, movieDate) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val movieDate = getItem(position)

        val binding: SpinnerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.spinner_item, parent, false
        )

        binding.item = movieDate?.label

        return binding.root
    }
}