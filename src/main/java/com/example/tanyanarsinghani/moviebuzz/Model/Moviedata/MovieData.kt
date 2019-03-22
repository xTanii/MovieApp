package com.example.tanyanarsinghani.moviebuzz.Model.Moviedata

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName= "Movies_DB")
data  class MovieData(
    @PrimaryKey @ColumnInfo(name ="Id" ) val id: Int,
    @ColumnInfo(name = "Adult") val adult:Boolean?,
    @ColumnInfo(name = "Backdrop_path") val backdrop_path:String?,
    //@ColumnInfo(name = "Belongs_to_collection") val belongs_to_collection: String?,
    @ColumnInfo(name = "Budget") val budget: Int?,
    //@ColumnInfo(name = "Genres") val genres: List<String>?,
    @ColumnInfo(name = "Homepage") val homepage: String?,
    @ColumnInfo(name = "imdb_id") val imdb_id: String?,
    @ColumnInfo(name = "Original_language") val original_language: String?,
    @ColumnInfo(name = "Original_title") val original_title: String?,
    @ColumnInfo(name = "Overview") val overview: String?,
    @ColumnInfo(name = "Popularity") val popularity: Double?,
    @ColumnInfo(name = "Poster_path") val poster_path: String?,
   // @ColumnInfo(name = "Production_companies") val production_companies: List<String>?,
   // @ColumnInfo(name = "Production_countries") val production_countries: List<String>?,
    @ColumnInfo(name = "Release_date") val release_date: String?,
    @ColumnInfo(name = "Revenue") val revenue: Int?,
    @ColumnInfo(name = "Runtime") val runtime: Int?,
   // @ColumnInfo(name = "Spoken_languages") val spoken_languages: List<String>?,
    @ColumnInfo(name = "Status") val status: String?,
    @ColumnInfo(name = "Tagline") val tagline: String?,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Video") val video: Boolean?,
    @ColumnInfo(name = "Vote_average") val vote_average: Double?,
    @ColumnInfo(name = "Vote_count") val vote_count: Int?,
    @ColumnInfo(name = "Favourite") var  favourite:Boolean=false

)