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
                if(movieData.posterImage==null){
                    intent.putExtra("posterUrl", "https://i.pinimg.com/564x/50/6f/3c/506f3c5fcb942c54fbe2f5f84b96d2c8.jpg")
                } else{
                    intent.putExtra("posterUrl", "https://image.tmdb.org/t/p/original${movieData.posterImage}")
                }
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