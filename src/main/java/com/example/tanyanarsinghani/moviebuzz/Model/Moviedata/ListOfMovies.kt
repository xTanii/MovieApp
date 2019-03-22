package com.example.tanyanarsinghani.moviebuzz.Model.Moviedata

import com.google.gson.annotations.SerializedName

data class ListOfMovies(
    @SerializedName("results")
    val moviedatas: List<MovieData>
)

