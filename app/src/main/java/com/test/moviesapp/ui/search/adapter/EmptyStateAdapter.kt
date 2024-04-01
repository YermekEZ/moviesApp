package com.test.moviesapp.ui.search.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class EmptyStateAdapter(private val context: Context, private val text: String) : BaseAdapter() {

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return text
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = TextView(context)
        textView.text = text
        textView.gravity = Gravity.CENTER
        textView.setPadding(16, 16, 16, 16)
        return textView
    }
}
