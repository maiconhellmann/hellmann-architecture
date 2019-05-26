package com.hellmann.archticture.feature.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hellmann.archticture.R
import com.hellmann.archticture.util.extensions.inflate
import com.hellmann.archticture.util.extensions.load
import com.hellmann.archticture.util.extensions.visible
import com.hellmann.domain.entity.Article
import kotlinx.android.synthetic.main.fragment_article_item.view.image
import kotlinx.android.synthetic.main.fragment_article_item.view.subtitle
import kotlinx.android.synthetic.main.fragment_article_item.view.title

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var articles: List<Article> = listOf()

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.fragment_article_item)) {

        fun bind(article: Article) = with(itemView) {
            title.text = article.title
            subtitle.text = article.publishedAt
            subtitle.visible(article.publishedAt.isNullOrBlank().not())
            image.load(article.urlToImage)

            itemView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun getItemCount(): Int = articles.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }
}