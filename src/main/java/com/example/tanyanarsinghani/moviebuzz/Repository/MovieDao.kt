package com.example.tanyanarsinghani.moviebuzz.Repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
//import com.example.tanyanarsinghani.moviebuzz.Model.Model
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData

@Dao
public interface MovieDao{
    /* @Query("SELECT * FROM NEWS_TABLE")
     fun getAll():List<Article>*/
    /*@Insert(onConflict = REPLACE)
    fun SaveCurrentArticleDetails(article: AsyncEntity)

    @Query("SELECT * FROM NEWS_TABLE where URL= :url")
    fun getArticleDetails(url: String): AsyncEntity*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<MovieData>): List<Long>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertfav(movies: MovieData):Long


    @Query("SELECT * FROM Movies_DB where SEARCH LIKE :search ")
    fun getAll(search:String): LiveData<List<MovieData>>

    @Query("SELECT * FROM Movies_DB where ID LIKE :id ")
    fun getMovDetails(id:Int): LiveData<MovieData>



    @Query("SELECT * FROM Movies_DB where Favourite LIKE :fav ")
    fun getAllFav(fav:Boolean):LiveData<List<MovieData>>



    @Query("SELECT * FROM Movies_DB WHERE Id LIKE :id")
    fun getNews(id: Int): List<MovieData>

    //@Query("SELECT * FROM Movies_DB where tag LIKE :tag")
   // fun getArticle(tag: String): LiveData<List<MovieData>>
}