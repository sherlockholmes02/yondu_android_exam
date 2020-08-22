package com.davedecastro.yonduandroidexam.data.db.entities

import com.google.gson.annotations.SerializedName

data class CinemaDate(
    var parent: String,
    @SerializedName("cinemas")
    var cinemasNumbers: List<CinemaNumber>
)