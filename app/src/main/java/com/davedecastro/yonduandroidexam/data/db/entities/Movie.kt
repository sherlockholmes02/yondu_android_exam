package com.davedecastro.yonduandroidexam.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @SerializedName("movie_id")
    var movieId: String,
    @SerializedName("advisory_rating")
    var advisoryRating: String,
    @SerializedName("canonical_title")
    var canonicalTitle: String,
    var cast: List<String>,
    var genre: String,
    @SerializedName("has_schedules")
    var hasSchedules: Int,
    @SerializedName("is_inactive")
    var isInActive: Int,
    @SerializedName("is_showing")
    var isShowing: Int,
    @SerializedName("link_name")
    var linkName: String,
    var poster: String,
    @SerializedName("poster_landscape")
    var posterLandscape: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("runtime_mins")
    var runtimeMins: String,
    var synopsis: String,
    var trailer: String,
    @SerializedName("average_rating")
    var averageRating: String,
    @SerializedName("total_reviews")
    var totalReviews: String,
    var variants: List<String>,
    var theater: String,
    var order: Int,
    @SerializedName("is_featured")
    var isFeatured: Int,
    @SerializedName("watch_list")
    var watchList: Int,
    @SerializedName("your_rating")
    var yourRating: Int
)