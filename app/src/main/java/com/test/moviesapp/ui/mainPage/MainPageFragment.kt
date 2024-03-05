package com.test.moviesapp.ui.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.moviesapp.databinding.FragmentMainPageBinding
import com.test.moviesapp.ui.mainPage.adapter.MoviesRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private val viewModel: MainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        observeViewModel()
    }

    private fun setOnClickListener() = with(binding) {
//        getMoviesButton.setOnClickListener {
//            viewModel.getPopularMovies()
//        }
    }

    private fun observeViewModel() = with(viewModel) {
        movies.observe(viewLifecycleOwner) {
            val adapter = MoviesRecyclerViewAdapter(
                movies = it,
                onMovieClicked = {

                }
            )
            binding.recyclerView.adapter = adapter
        }
    }
}