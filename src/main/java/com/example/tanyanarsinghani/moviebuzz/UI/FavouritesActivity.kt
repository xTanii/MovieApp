package com.example.tanyanarsinghani.moviebuzz.UI

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.tanyanarsinghani.moviebuzz.API.DatabaseClient
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData
import com.example.tanyanarsinghani.moviebuzz.R
import com.example.tanyanarsinghani.moviebuzz.Repository.MovieRepository
import com.example.tanyanarsinghani.moviebuzz.UI.Adapter.FavouriteAdapter
import kotlinx.android.synthetic.main.activity_favourites.*

@Suppress("DEPRECATION")

open class FavouritesActivity : AppCompatActivity(),
    FavouriteAdapter.ItemClickListener {

    val data = ArrayList<MovieData>()
    private lateinit var db: DatabaseClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        db = DatabaseClient.getInstance(this)
        val fav:Boolean=true
        var datas = MovieRepository.getInstance(db).getFav(fav)
        datas.observe(this@FavouritesActivity, android.arch.lifecycle.Observer {
            it?.let {
                Toast.makeText(this@FavouritesActivity,"Getting data from db", Toast.LENGTH_SHORT)
                    .show()
                data.clear()
                data.addAll(it)
                (favrc.adapter as FavouriteAdapter).setData(it)
                favrc.adapter?.notifyDataSetChanged()
            } ?: Toast.makeText(this@FavouritesActivity, "Empty data", Toast.LENGTH_SHORT)
                .show()
        })


        favrc.layoutManager = GridLayoutManager(this@FavouritesActivity,2) as RecyclerView.LayoutManager?
        favrc.adapter = FavouriteAdapter(data, this@FavouritesActivity)




        val actionbar = supportActionBar
        actionbar!!.title = "MovieBuzz"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }




    override fun onClick(moviedata: MovieData) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("id", moviedata.id)
        intent.putExtra("Favourite",moviedata.favourite)
        startActivity(intent)

    }








override fun onSupportNavigateUp(): Boolean {

    onBackPressed()
    return true
}
}