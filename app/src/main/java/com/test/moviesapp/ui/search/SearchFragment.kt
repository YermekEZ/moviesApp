package com.test.moviesapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.databinding.FragmentSearchBinding
import com.test.moviesapp.ui.movieDetails.MovieDetailActivity
import com.test.moviesapp.ui.search.adapter.EmptyStateAdapter
import com.test.moviesapp.ui.search.adapter.RecentlySearchedMoviesRecyclerViewAdapter
import com.test.moviesapp.ui.search.adapter.SearchResultPopUpAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var popUpWindow: ListPopupWindow
    private lateinit var popUpWindowAdapter: SearchResultPopUpAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeViewModel()
        viewModel.getRecentlySearchedMovies()
    }

    private fun setupView() = with(binding) {
        popUpWindow = ListPopupWindow(requireContext())
        popUpWindowAdapter = SearchResultPopUpAdapter(requireContext(), emptyList())
        popUpWindow.setAdapter(popUpWindowAdapter)
        popUpWindow.anchorView = searchContainer
        popUpWindow.height = 320 * resources.displayMetrics.density.toInt()

        popUpWindow.setOnItemClickListener { _, _, position, _ ->
            (popUpWindowAdapter.getItem(position) as? MovieModel)?.let { movie ->
                viewModel.addMovieToRecentlySearched(movie)
                val intent = Intent(requireContext(), MovieDetailActivity::class.java)
                intent.putExtra("id", movie.id)
                intent.putExtra("title", movie.title)
                intent.putExtra("description", movie.description)
                intent.putExtra("rating", movie.voteAverage)
                if(movie.posterImage==null){
                    intent.putExtra("posterUrl", "https://i.pinimg.com/564x/50/6f/3c/506f3c5fcb942c54fbe2f5f84b96d2c8.jpg")
                } else{
                    intent.putExtra("posterUrl", "https://image.tmdb.org/t/p/original${movie.posterImage}")
                }
                startActivity(intent)
            }
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 3) {
                    viewModel.searchMovies(p0.toString())
                }
            }
        })
    }

    private fun observeViewModel() = with(viewModel) {
        moviesListFromSearch.observe(viewLifecycleOwner) { movies ->
            if (movies.isEmpty()) {
                popUpWindow.height = 45 * resources.displayMetrics.density.toInt()
                val emptyStateAdapter = EmptyStateAdapter(requireContext(), "Nothing found")
                popUpWindow.setAdapter(emptyStateAdapter)
                popUpWindow.show()
            } else {
                popUpWindowAdapter = SearchResultPopUpAdapter(requireContext(), movies)
                popUpWindow.setAdapter(popUpWindowAdapter)
                popUpWindow.show()
            }
        }
        recentlySearchedMovies.observe(viewLifecycleOwner) { recentlySearchedMovies ->
            if (recentlySearchedMovies.isEmpty()) {
                binding.emptyRecentStateImageView.visibility = View.VISIBLE
                binding.emptyRecentStateTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyRecentStateTextView.visibility = View.GONE
                binding.emptyRecentStateImageView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

                val adapter = RecentlySearchedMoviesRecyclerViewAdapter(recentlySearchedMovies) { movie ->
                    val intent = Intent(requireContext(), MovieDetailActivity::class.java)
                    intent.putExtra("id", movie.id)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("description", movie.description)
                    intent.putExtra("rating", movie.voteAverage)
                    intent.putExtra(
                        "posterUrl",
                        "https://image.tmdb.org/t/p/original${movie.posterImage}"
                    )
                    startActivity(intent)
                }
                binding.recyclerView.adapter = adapter
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.searchEditText.clearFocus()
        binding.searchEditText.setText("")
        popUpWindow.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        popUpWindow.dismiss()
        _binding = null
    }
}
