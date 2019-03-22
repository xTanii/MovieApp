package com.example.tanyanarsinghani.moviebuzz.API


import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.ListOfMovies
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData
import com.example.tanyanarsinghani.moviebuzz.Model.YoutubeVideoTrailerData.Results
//import com.example.tanyanarsinghani.newsapp.data.Repository.AsyncDAO
import retrofit2.http.GET
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieAPI {
    @GET("discover/movie?api_key=6e6cfc7293c204396c61a08a86735d6f&language=en-US")
    fun getAll():retrofit2.Call<ListOfMovies>


    @GET("discover/movie?api_key=6e6cfc7293c204396c61a08a86735d6f&language=en-US")
    fun Sort(@Query("sort_by") sort_by: String, @Query("include_adult") include_adult: Boolean, @Query("include_video") include_video: Boolean,@Query("pg") pg: Int): retrofit2.Call<ListOfMovies>

    @GET("movie/{id}")
    fun getcurrentMovie(@Path("id") id: Int, @Query("api_key") api_key:String,@Query("video") video:Boolean):retrofit2.Call<MovieData>

    @GET("movie/{id}/videos")
    fun gettrailerMovie(@Path("id") id: Int ,@Query("api_key") api_key:String):retrofit2.Call<Results>

}