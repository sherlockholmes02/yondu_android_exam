package com.davedecastro.yonduandroidexam.data.db.entities

data class Seatmap(
    var seatmap: List<List<String>>,
    var available: AvailableSeats
)