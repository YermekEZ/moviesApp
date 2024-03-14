package com.test.moviesapp.ui.mainPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.domain.useCases.model.PopularMovieModel
import com.test.moviesapp.databinding.LayoutMoviePosterItemBinding

class MoviesRecyclerViewAdapter(
    val onMovieClicked: (PopularMovieModel) -> Unit
): PagingDataAdapter<PopularMovieModel, MoviesRecyclerViewAdapter.ViewHolder>(MoviesListDiffCallback()) {  //: RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutMoviePosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PopularMovieModel? = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val newItem = payloads[0] as PopularMovieModel
            holder.bind(newItem)
        }
    }

    class MoviesListDiffCallback : DiffUtil.ItemCallback<PopularMovieModel>() {
        override fun areItemsTheSame(
            oldItem: PopularMovieModel,
            newItem: PopularMovieModel
        ) = oldItem.title == newItem.title

        override fun areContentsTheSame(
            oldItem: PopularMovieModel,
            newItem: PopularMovieModel
        ) = oldItem == newItem

    }

    inner class ViewHolder(private val binding: LayoutMoviePosterItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: PopularMovieModel) = with(binding) {
            moviePosterImageView.load("https://image.tmdb.org/t/p/original${movie.posterImage}")
            movieTitleTextView.text = movie.title
            movieRatingTextView.text = movie.voteAverage.toString()
            binding.root.setOnClickListener {
                onMovieClicked.invoke(movie)
            }
        }
    }
}