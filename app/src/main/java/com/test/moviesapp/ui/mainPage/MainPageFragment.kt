package com.test.moviesapp.ui.mainPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.test.moviesapp.databinding.FragmentMainPageBinding
import com.test.moviesapp.ui.mainPage.adapter.MoviesRecyclerViewAdapter
import com.test.moviesapp.ui.movieDetails.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainPageFragment : Fragment() {

    private var _binding: FragmentMainPageBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var adapter: MoviesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeViewModel()
        getPopularMovies()
    }

    private fun setupView() = with(binding) {
        adapter = MoviesRecyclerViewAdapter { movieData ->
            //viewModel.saveMovie(movieData)
            val intent = Intent(requireContext(), MovieDetailActivity::class.java)
            intent.putExtra("id", movieData.id)
            intent.putExtra("title", movieData.title)
            intent.putExtra("description", movieData.description)
            intent.putExtra("rating", movieData.voteAverage)
            intent.putExtra("posterUrl", "https://image.tmdb.org/t/p/original${movieData.posterImage}")
            if(movieData.posterImage==null){
                intent.putExtra("posterUrl", "https://i.pinimg.com/564x/50/6f/3c/506f3c5fcb942c54fbe2f5f84b96d2c8.jpg")
            } else{
                intent.putExtra("posterUrl", "https://image.tmdb.org/t/p/original${movieData.posterImage}")
            }
            Log.v("Test log", movieData.description)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() = with(viewModel) {

    }

    private fun getPopularMovies() {
        lifecycleScope.launch {
            viewModel.getPagedMovies().observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}