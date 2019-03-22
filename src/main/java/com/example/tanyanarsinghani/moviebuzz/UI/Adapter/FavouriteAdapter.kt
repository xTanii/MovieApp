package com.example.tanyanarsinghani.moviebuzz.UI.Adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData
import com.example.tanyanarsinghani.moviebuzz.R
import com.example.tanyanarsinghani.moviebuzz.UI.MovieDetailsActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favlayout.view.*
import java.lang.Exception

class FavouriteAdapter(var movieData: List<MovieData>, val itemClickListener : ItemClickListener? = null) : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(moviedata: MovieData) {

            view.setOnClickListener{
                val intent= Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra("Text",view.favmoviename.text)
                intent.putExtra("Image",view.mbiv.imageTintList)


            }
        }


        var context = view.context
        val movieposter = view.findViewById<ImageView>(R.id.mbiv)
        var moviename = view.findViewById<TextView>(R.id.favmoviename)


    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.favlayout, p0, false);

        return ViewHolder(view)
    }

    override fun getItemCount() = movieData.size
    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {


        var movieposterpath=movieData[p1].poster_path
        var myUri = Uri.parse("https://image.tmdb.org/t/p/original/" + movieposterpath)

        p0.moviename.text = movieData[p1].title
        p0.bind(movieData[p1])
        Picasso.get()
            .load(myUri)
            .placeholder(R.drawable.ic_launcher_background)
            .into(p0.movieposter, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Log.d("exception",e?.message)
                }
            })

        p0.itemView.setOnClickListener { itemClickListener?.onClick(movieData[p1]) }

    }



    interface ItemClickListener {
        fun onClick(moviedata: MovieData)
    }

    fun setData(moviedata: List<MovieData>){
        this.movieData = moviedata
    }

}