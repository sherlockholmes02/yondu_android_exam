package com.davedecastro.yonduandroidexam.data.db.entities

import com.google.gson.annotations.SerializedName

data class MovieTime(
    var id: String,
    var label: String,
    @SerializedName("schedule_id")
    var scheduleId: String,
    @SerializedName("popcorn_price")
    var popcornPrice: String,
    @SerializedName("popcorn_label")
    var popcornLabel: String,
    @SerializedName("seating_type")
    var seatingType: String,
    var type: String,
    var variant: String?
)