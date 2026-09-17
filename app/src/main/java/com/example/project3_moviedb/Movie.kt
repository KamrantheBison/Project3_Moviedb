package com.example.project3_moviedb

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    // Convenience property for the full poster URL
    val posterImageUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
}