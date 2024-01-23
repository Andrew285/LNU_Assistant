package com.example.lnu_assistant.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lnu_assistant.databinding.FragmentNewsItemBinding

class NewsAdapter(var context: Context, private var newsList: List<News>):
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    private lateinit var binding: FragmentNewsItemBinding
    class MyViewHolder(private var binding: FragmentNewsItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.news = news
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = FragmentNewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news: News = newsList[position]
        holder.bind(news)

        Glide.with(context)
            .load(news.imageUrl)
            .into(binding.thumbnailImageCard)
    }
}