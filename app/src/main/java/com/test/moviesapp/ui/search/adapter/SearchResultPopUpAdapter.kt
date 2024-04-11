package com.test.moviesapp.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.load
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.databinding.LayoutSearchItemBinding

class SearchResultPopUpAdapter(
    private val context: Context,
    private var result: List<MovieModel>
) : BaseAdapter() {

    override fun getCount(): Int {
        return result.size
    }

    override fun getItem(position: Int): Any {
        return result[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: LayoutSearchItemBinding

        if (convertView == null) {
            binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            binding = LayoutSearchItemBinding.bind(convertView)
        }

        val item = result[position]
        if(item.posterImage==null){
            binding.posterImageView.load("https://i.pinimg.com/564x/50/6f/3c/506f3c5fcb942c54fbe2f5f84b96d2c8.jpg")
        } else{
            binding.posterImageView.load("https://image.tmdb.org/t/p/original${item.posterImage}")
        }
        binding.movieTitleTextView.text = item.title
        binding.ratingTextView.text = item.voteAverage.toString()

        return binding.root
    }


}