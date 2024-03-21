package com.test.moviesapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.databinding.LayoutMoviePosterItemBinding

class RecentlySearchedMoviesRecyclerViewAdapter(
    private val movies: List<MovieModel>,
    val onMovieClicked: (MovieModel) -> Unit
) : RecyclerView.Adapter<RecentlySearchedMoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutMoviePosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: LayoutMoviePosterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) = with(binding) {
            moviePosterImageView.load("https://image.tmdb.org/t/p/original${movie.posterImage}")
            movieTitleTextView.text = movie.title
            movieRatingTextView.text = movie.voteAverage.toString()
            binding.root.setOnClickListener {
                onMovieClicked.invoke(movie)
            }
        }
    }
}