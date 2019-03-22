package com.example.tanyanarsinghani.moviebuzz.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MovieAPIService {
    fun create():MovieAPI
    {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")

            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service=retrofit.create(MovieAPI::class.java)
        return service

    }
}