package com.davedecastro.yonduandroidexam.data.db.entities

import com.google.gson.annotations.SerializedName

data class CinemaNumber(
    var id: String,
    @SerializedName("cinema_id")
    var cinemaId: String,
    var label: String
)