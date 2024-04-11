package com.test.moviesapp.ui.favourites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.databinding.LayoutMoviePosterItemBinding

class FavouriteMoviesRecyclerViewAdapter(
    private val movies: List<MovieModel>,
    val onMovieClicked: (MovieModel) -> Unit
) : RecyclerView.Adapter<FavouriteMoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMoviesRecyclerViewAdapter.ViewHolder {
        val binding =
            LayoutMoviePosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: FavouriteMoviesRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: LayoutMoviePosterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) = with(binding) {
            if(movie.posterImage==null){
                moviePosterImageView.load("https://i.pinimg.com/564x/50/6f/3c/506f3c5fcb942c54fbe2f5f84b96d2c8.jpg")
            } else{
                moviePosterImageView.load("https://image.tmdb.org/t/p/original${movie.posterImage}")
            }
            movieTitleTextView.text = movie.title
            movieRatingTextView.text = movie.voteAverage.toString()
            binding.root.setOnClickListener {
                onMovieClicked.invoke(movie)
            }
        }
    }
}