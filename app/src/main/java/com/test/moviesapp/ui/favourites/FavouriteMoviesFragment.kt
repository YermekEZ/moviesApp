package com.test.moviesapp.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.moviesapp.databinding.FragmentFavouritesBinding
import com.test.moviesapp.ui.favourites.adapter.FavouriteMoviesRecyclerViewAdapter
import com.test.moviesapp.ui.movieDetails.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: FavouriteMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        observeViewModel()
        viewModel.getMovies()
    }

    private fun observeViewModel() = with(viewModel) {
        favouriteMovies.observe(viewLifecycleOwner) {
            val adapter = FavouriteMoviesRecyclerViewAdapter(
                movies = it
            ) { movieData ->
                val intent = Intent(requireContext(), MovieDetailActivity::class.java)
                intent.putExtra("id", movieData.id)
                intent.putExtra("title", movieData.title)
                intent.putExtra("description", movieData.description)
                intent.putExtra("rating", movieData.voteAverage)
                intent.putExtra("posterUrl", "https://image.tmdb.org/t/p/original${movieData.posterImage}")
                startActivity(intent)
            }
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}