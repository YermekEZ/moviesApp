package com.test.moviesapp.ui.movieDetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.databinding.ActivityMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()

    private lateinit var movieData: MovieModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            movieData = MovieModel(
                id = it.getInt("id"),
                title = it.getString("title") ?: "No title",
                description = it.getString("description").orEmpty(),
                voteAverage = it.getDouble("rating") ?: 0.0,
                posterImage = it.getString("posterUrl").orEmpty()
            )
        }
        setupView()
        setOnClickListeners()
        observeViewModel()
        viewModel.getVideoInfo(movieId = movieData.id.toString())
    }

    private fun setupView() = with(binding) {
        moviePosterImageView.load(movieData.posterImage)
        titleTextView.text = movieData.title
        detailsTextView.text = movieData.description
        ratingTextView.text = movieData.voteAverage.toString()
        lifecycle.addObserver(youtubePlayer)
    }

    private fun setOnClickListeners() = with(binding) {
        addMovieButton.setOnClickListener {
            viewModel.addMovieToFavorites(movieData)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        videoKey.observe(this@MovieDetailActivity) {
            binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    it?.let {
                        youTubePlayer.loadVideo(it, 0F)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayer.release()
    }
}