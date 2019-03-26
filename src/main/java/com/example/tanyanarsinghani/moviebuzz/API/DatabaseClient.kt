package com.example.tanyanarsinghani.moviebuzz.API

import android.arch.persistence.room.Room
import android.content.Context
import com.example.tanyanarsinghani.moviebuzz.Repository.MovieDatabase

class DatabaseClient {
    private var mCtx: Context? = null

    private  var appDatabase: MovieDatabase


    private constructor (mCtx: Context) {
        this.mCtx = mCtx
        appDatabase = Room.databaseBuilder(mCtx, MovieDatabase::class.java!!, "MyToDos")
            .build()
    }

    companion object {
        private  var mInstance: DatabaseClient?=null


        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }

            return mInstance!!
        }
    }

    fun getAppDatabase(): MovieDatabase {
        return appDatabase
    }


}

