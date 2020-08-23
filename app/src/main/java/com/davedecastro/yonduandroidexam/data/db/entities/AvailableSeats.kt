package com.davedecastro.yonduandroidexam.data.db.entities

import com.google.gson.annotations.SerializedName

data class AvailableSeats(
    var seats: List<String>,
    @SerializedName("seat_count")
    var seatCount: Int
)