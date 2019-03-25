package com.example.tanyanarsinghani.moviebuzz.UI

import android.arch.lifecycle.LiveData
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.tanyanarsinghani.moviebuzz.API.DatabaseClient
import com.example.tanyanarsinghani.moviebuzz.API.MovieAPIService
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.ListOfMovies
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData
import com.example.tanyanarsinghani.moviebuzz.R
import com.example.tanyanarsinghani.moviebuzz.Repository.MovieRepository
import com.example.tanyanarsinghani.moviebuzz.UI.Adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_movie_buzz_first.*
import retrofit2.Response
@Suppress("DEPRECATION")

    open class MovieBuzzFirstActivity : AppCompatActivity(),
        MovieAdapter.ItemClickListener {

        val data = ArrayList<MovieData>()
    var search = "Popularity"
    private lateinit var db: DatabaseClient
    var datas:LiveData<List<MovieData>>?=null


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_movie_buzz_first)
            var moviedata = MovieAPIService.create()
            db = DatabaseClient.getInstance(this)
            datas = MovieRepository.getInstance(db).getAll(search)
            datas!!.observe(this@MovieBuzzFirstActivity, android.arch.lifecycle.Observer {
                it?.let {
                    Toast.makeText(this@MovieBuzzFirstActivity,"Getting data from db", Toast.LENGTH_SHORT)
                        .show()
                    //data.clear()
                    data.addAll(it)
                    (mbrc.adapter as MovieAdapter).setData(it)
                    mbrc.adapter?.notifyDataSetChanged()
                } ?: Toast.makeText(this@MovieBuzzFirstActivity, "Empty data", Toast.LENGTH_SHORT)
                    .show()
            })
            favlist.setOnClickListener {

                val intent = Intent(this, FavouritesActivity::class.java)
                startActivity(intent)
            }

            MovieRepository.getInstance(db).getAll(object :MovieRepository.MovieCallback
            {
                override fun onSuccess(t: Any) {
                    val listofmoviedata = (t as Response<ListOfMovies>).body()

                    if (listofmoviedata != null) {
                        data.clear()
                        data.addAll(listofmoviedata.moviedatas)
                        mbrc.adapter?.notifyDataSetChanged()

                        MovieRepository.getInstance(db).saveMovie(listofmoviedata.moviedatas)
                        //MovieRepository.getInstance(db).getAll()


                    }


                }

                override fun onError() {
                    Log.d("Yomama", "message")

                }

            })
            mbrc.layoutManager = GridLayoutManager(this@MovieBuzzFirstActivity,2) as RecyclerView.LayoutManager?
            mbrc.adapter = MovieAdapter(data, this@MovieBuzzFirstActivity)

            spinner()

        }




    fun spinner() {
        val choice = arrayOf("Popularity", "Highest-Rated")
        val spinner = findViewById<Spinner>(R.id.moviediscoverspinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, choice)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(this@MovieBuzzFirstActivity, choice[position], Toast.LENGTH_LONG).show()
                    var moviedata = MovieAPIService.create()
                    if(data!=null)
                    {
                        search=choice[position].toLowerCase()
                        val include_adult:Boolean=false
                        val include_video:Boolean=true
                        val  pg=1
                        Toast.makeText(this@MovieBuzzFirstActivity, search, Toast.LENGTH_SHORT).show()


                        //moviedata.Sort( search,include_adult,include_video,pg).enqueue(this@MovieBuzzFirstActivity)
                        MovieRepository.getInstance(db)
                            .Sort( search,include_adult,include_video,pg,object : MovieRepository.MovieCallback {

                                override fun onSuccess(t: Any) {
                                    var moviesorted = (t as Response<ListOfMovies>).body()
                                    if (moviesorted != null) {
                                        data.clear()
                                        data.addAll(moviesorted.moviedatas)
                                        mbrc.adapter?.notifyDataSetChanged()
                                        for(moviedatas in data.iterator())
                                        {
                                            moviedatas.search = search
                                        }
                                        MovieRepository.getInstance(db).saveMovie(moviesorted.moviedatas)

                                        }
                                    }


                                override fun onError() {
                                    Log.d("Yomama", "sdffd")

                                }


                            })

                    }
                        else
                    {
                        var moviedata = MovieAPIService.create()
                        search=choice[position].toLowerCase()
                        val include_adult:Boolean=false
                        val include_video:Boolean=false
                        val  pg=1
                        Toast.makeText(this@MovieBuzzFirstActivity, search, Toast.LENGTH_SHORT).show()



                        MovieRepository.getInstance(db)
                            .Sort( search,include_adult,include_video,pg,object : MovieRepository.MovieCallback {

                                override fun onSuccess(t: Any) {
                                    var moviesorted = (t as Response<ListOfMovies>).body()
                                    if (moviesorted != null) {
                                        data.clear()
                                        data.addAll(moviesorted.moviedatas)
                                        mbrc.adapter?.notifyDataSetChanged()
                                        for(moviedatas in data.iterator())
                                        {
                                            moviedatas.search = search
                                        }

                                        MovieRepository.getInstance(db).saveMovie(moviesorted.moviedatas)

                                    }
                                }


                                override fun onError() {
                                    Log.d("Yomama", "sdffd")

                                }


                            })

                    }
                    datas = MovieRepository.getInstance(db).getAll(search)

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }

            override fun onClick(moviedata: MovieData) {
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("id", moviedata.id)
                intent.putExtra("Favourite",moviedata.favourite)
            startActivity(intent)

        }






    }