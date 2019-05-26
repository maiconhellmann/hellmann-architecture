package com.hellmann.archticture.feature.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hellmann.archticture.R
import com.hellmann.archticture.extension.inflate
import com.hellmann.domain.entity.Article
import kotlinx.android.synthetic.main.fragment_article_item.view.title

class ArticlesAdapter: RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var jobs: List<Article> = listOf()

    inner class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.fragment_article_item)) {

        fun bind(Article: Article) = with(itemView) {
            title.text = Article.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)
    override fun getItemCount(): Int = jobs.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(jobs[position])
}