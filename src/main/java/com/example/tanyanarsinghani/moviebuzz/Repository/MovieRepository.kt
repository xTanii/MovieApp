package com.example.tanyanarsinghani.moviebuzz.Repository

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.tanyanarsinghani.moviebuzz.API.DatabaseClient
import com.example.tanyanarsinghani.moviebuzz.API.MovieAPIService
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.ListOfMovies
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData
import com.example.tanyanarsinghani.moviebuzz.Model.YoutubeVideoTrailerData.Results
import com.example.tanyanarsinghani.moviebuzz.UI.MovieBuzzFirstActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executors

@SuppressLint("StaticFieldLeak")
class MovieRepository : MovieBuzzFirstActivity {


    var databaseClient: DatabaseClient
    var integers = ArrayList<MovieData>()
    var article = Arrays.asList(integers)


    private constructor(databaseClient: DatabaseClient) {
        this.databaseClient = databaseClient
    }

    companion object {

        private var mInstance: MovieRepository? = null


        @Synchronized
        fun getInstance(databaseClient: DatabaseClient): MovieRepository {
            if (mInstance == null) {
                mInstance = MovieRepository(databaseClient)
            }

            return mInstance!!
        }

    }

    lateinit var currentMovie: MovieData
    fun saveMovie(data: List<MovieData>) {

        Executors.newSingleThreadScheduledExecutor().execute(object : Runnable {
            override fun run() {
                try {
                    val insert = databaseClient.getAppDatabase().MovieDao().insert(data)
                    Log.d("YoMama", "Number of inserter ${insert.size}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }
    fun saveMoviefav(data: MovieData) {

        Executors.newSingleThreadScheduledExecutor().execute(object : Runnable {
            override fun run() {
                try {
                    val insert = databaseClient.getAppDatabase().MovieDao().insertfav(data)
                    Log.d("YoMama", "Number of inserter ${insert}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }

    fun getAll(): LiveData<List<MovieData>> {
        return databaseClient.getAppDatabase().MovieDao().getAll()
    }
    fun getMovieDetails(id:Int): LiveData<MovieData> {
        return databaseClient.getAppDatabase().MovieDao().getMovDetails(id)
    }

    fun getFav(fav:Boolean): LiveData<List<MovieData>> {
        return databaseClient.getAppDatabase().MovieDao().getAllFav(fav)
    }


    fun getMovie(id: Int, apikey: String, video: Boolean,callback: MovieCallback) {
        val moviedata = MovieAPIService.create()
        moviedata.getcurrentMovie(id, apikey, video).enqueue(object : Callback<MovieData> {
            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                callback.onSuccess(response)
            }
        })
    }
    fun getAll(callback: MovieCallback) {
        val moviedata = MovieAPIService.create()
        moviedata.getAll().enqueue(object : Callback<ListOfMovies> {
            override fun onFailure(call: Call<ListOfMovies>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(call: Call<ListOfMovies>, response: Response<ListOfMovies>) {
                callback.onSuccess(response)
            }
        })

    }
    fun getAllFavourites(fav:Boolean):LiveData<List<MovieData>> {
        return databaseClient.getAppDatabase().MovieDao().getAllFav(fav)
    }


    fun Sort(search:String,include_adult:Boolean,include_video:Boolean,pg:Int,callback: MovieCallback) {
        val moviedata = MovieAPIService.create()
        moviedata.Sort(search,include_adult,include_video,pg).enqueue(object : Callback<ListOfMovies> {
            override fun onFailure(call: Call<ListOfMovies>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(call: Call<ListOfMovies>, response: Response<ListOfMovies>) {
                callback.onSuccess(response)
            }
        })
    }
    fun getTrailer(id: Int,  video: String,apikey: String,callback: TrailerCallback) {
        val moviedata = MovieAPIService.create()
        moviedata.gettrailerMovie(id,  apikey).enqueue(object : Callback<Results> {
            override fun onFailure(call: Call<Results>, t: Throwable) {
                callback.onError()
            }

            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                callback.onSuccess(response)
            }
        })
    }




    //fun getArticles(tag: String): LiveData<List<MovieData>> {
    // return databaseClient.getAppDatabase().MovieDao().getArticle(tag)
    // }
    override fun onClick(moviedata: MovieData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface MovieCallback {
        fun onSuccess(t: Any)
        fun onError()
    }
    interface TrailerCallback {
        fun onSuccess(t: Any)
        fun onError()
    }


}
