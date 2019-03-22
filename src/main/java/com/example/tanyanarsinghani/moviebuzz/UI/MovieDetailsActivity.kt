package com.example.tanyanarsinghani.moviebuzz.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.tanyanarsinghani.moviebuzz.API.DatabaseClient
import com.example.tanyanarsinghani.moviebuzz.API.MovieAPIService
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData
import kotlinx.android.synthetic.main.activity_moviesdetailed.*
import retrofit2.Response
import com.example.tanyanarsinghani.moviebuzz.Model.YoutubeVideoTrailerData.Results
import com.example.tanyanarsinghani.moviebuzz.Repository.MovieRepository
import com.squareup.picasso.Picasso


class MovieDetailsActivity: AppCompatActivity(){

  var  id:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.tanyanarsinghani.moviebuzz.R.layout.activity_moviesdetailed)
        val bundle: Bundle? = intent.extras
         id = bundle!!.getInt("id")
        var favourite=bundle!!.get("Favourite")
        val apikey = "6e6cfc7293c204396c61a08a86735d6f"
        val  video:Boolean=true
        var db=DatabaseClient.getInstance(this)
        if (id != null) {
            var moviedata = MovieAPIService.create()
            MovieRepository.getInstance(db).getMovie(id,apikey,video,object :MovieRepository.MovieCallback{
                override fun onSuccess(t: Any) {
                    var moviedetails = (t as Response<MovieData>).body()
                    if (moviedetails != null) {
                        var myUri = Uri.parse("https://image.tmdb.org/t/p/original/" + moviedetails.poster_path)
                        Picasso.get()
                            .load(myUri).fit().centerCrop()
                            .into(moviethumbnail)
                        moviename.text = moviedetails.original_title
                        movieoverview.text = moviedetails.overview
                        releasedate.text = moviedetails.release_date
                        averagerating.text = moviedetails.vote_average.toString()
                        val apikey = "6e6cfc7293c204396c61a08a86735d6f"
                        val video = "videos"

                        fav11.setOnClickListener {
                            if(favourite==false){
                                fav11.setImageResource(getResources().getIdentifier("heartfilled", "drawable", getPackageName()))
                                moviedetails.favourite=true
                                favourite=true
                                MovieRepository.getInstance(db).saveMoviefav(moviedetails)
                            } else if(favourite==true){
                                fav11.setImageResource(getResources().getIdentifier("heartblank", "drawable", getPackageName()))
                                favourite=false
                                moviedetails.favourite=false
                                MovieRepository.getInstance(db).saveMoviefav(moviedetails)


                            }




                        }


                        var moviedata = MovieAPIService.create()
                        val videos = "video"
                        MovieRepository.getInstance(db)
                            .getTrailer(id,  videos,apikey, object : MovieRepository.TrailerCallback {

                                override fun onSuccess(t: Any) {
                                    var movietrailers = (t as Response<Results>).body()
                                    if (movietrailers?.results != null && movietrailers?.results?.size > 0) {
                                        val  movie=movietrailers.results[0]
                                        val key=movie.key
                                        if(key!=null) {
                                            //var url = Uri.parse("https://www.youtube.com/watch?v=" + key)
                                            val url="https://www.youtube.com/watch?v=" + key
                                            movietrailer.setOnClickListener {
                                                val intent = Intent(
                                                    this@MovieDetailsActivity,
                                                    YoutubeTrailerActivity::class.java

                                                )
                                                intent.putExtra("url", url)
                                                startActivity(intent)
                                            }
                                        }
                                        else
                                            Toast.makeText(this@MovieDetailsActivity,"Can't play the video",Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onError() {
                                    Log.d("Yomama", "sdffd")

                                }


                            })
                    }
                }

                override fun onError() {
                    Log.d("Yomama", "sdffd")
                }

            })




        }
        var moviedatas= MovieRepository.getInstance(db).getMovieDetails(id)

        moviedatas.observe(this@MovieDetailsActivity, android.arch.lifecycle.Observer {
            it?.let {
                if(it.favourite==true)
                {
                    fav11.setImageResource(getResources().getIdentifier("heartfilled", "drawable", getPackageName()))
                    moviename.text = it.title
                    movieoverview.text = it.overview
                    releasedate.text = it.release_date
                    averagerating.text = it.vote_average.toString()
                }


                else if(it.favourite==false)
                {
                    fav11.setImageResource(getResources().getIdentifier("heartblank", "drawable", getPackageName()))
                    moviename.text = it.title
                    movieoverview.text = it.overview
                    releasedate.text = it.release_date
                    averagerating.text = it.vote_average.toString()
                }

                Toast.makeText(this@MovieDetailsActivity,"Getting data from db", Toast.LENGTH_SHORT)
                    .show()


            } ?: Toast.makeText(this@MovieDetailsActivity, "Empty data", Toast.LENGTH_SHORT)
                .show()
        })


        val actionbar = supportActionBar
        actionbar!!.title = "MovieBuzz"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }


        override fun onSupportNavigateUp(): Boolean {

            onBackPressed()
            return true

        }

    }



