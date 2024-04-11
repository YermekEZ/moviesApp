package com.test.moviesapp.ui.mainPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.databinding.LayoutMoviePosterItemBinding

class MoviesRecyclerViewAdapter(
    val onMovieClicked: (MovieModel) -> Unit
): PagingDataAdapter<MovieModel, MoviesRecyclerViewAdapter.ViewHolder>(MoviesListDiffCallback()) {  //: RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutMoviePosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: MovieModel? = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val newItem = payloads[0] as MovieModel
            holder.bind(newItem)
        }
    }

    class MoviesListDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ) = oldItem == newItem

    }

    inner class ViewHolder(private val binding: LayoutMoviePosterItemBinding): RecyclerView.ViewHolder(binding.root) {
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