package com.davedecastro.yonduandroidexam.data.db.entities

data class Schedule(
    var dates: List<MovieDate>,
    var cinemas: List<CinemaDate>,
    var times: List<TimesDate>
)