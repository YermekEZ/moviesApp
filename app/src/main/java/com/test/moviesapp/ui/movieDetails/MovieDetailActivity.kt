package com.test.moviesapp.ui.movieDetails

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.SampleService
import com.test.moviesapp.databinding.ActivityMovieDetailBinding
import com.test.moviesapp.sharedPrefs.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel by viewModels<MovieDetailViewModel>()

    private lateinit var movieData: MovieModel

    private val notificationPermissionStatus = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            Intent(applicationContext, SampleService::class.java).apply {
                this.action = SampleService.ServiceAction.START.toString()
                startService(this)
            }
        } else {
            // permission is not granted
        }
    }
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
        //LocalNotification(this@MovieDetailActivity.applicationContext).showNotification("Test title", "Notification's description goes here")
        lifecycle.addObserver(youtubePlayer)
        if (SharedPreferences.getSharedPreference(applicationContext).getBoolean("IS_FIRST_LAUNCH", false)) {
            // TODO show instructions
            SharedPreferences.getSharedPreference(applicationContext).edit().putBoolean("IS_FIRST_LAUNCH", false).apply()
        }
    }

    private fun setOnClickListeners() = with(binding) {
        addMovieButton.setOnClickListener {
            viewModel.addMovieToFavorites(movieData)
        }
        startServiceButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // ask runtime permission
                val isPushPermissionIsGiven = ContextCompat.checkSelfPermission(this@MovieDetailActivity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
                if (!isPushPermissionIsGiven) {
                    val showRationale = shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                    if (showRationale) {
                        // say to user that permission should be given
                    } else {
                        notificationPermissionStatus.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                } else {
                    // forgot else branch
                    Intent(applicationContext, SampleService::class.java).apply {
                        this.action = SampleService.ServiceAction.START.toString()
                        startService(this)
                    }
                }
            } else {
                Intent(applicationContext, SampleService::class.java).apply {
                    this.action = SampleService.ServiceAction.START.toString()
                    startService(this)
                }
            }
        }
        stopServiceButton.setOnClickListener {
            Intent(applicationContext, SampleService::class.java).apply {
                this.action = SampleService.ServiceAction.STOP.toString()
                startService(this)
            }
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