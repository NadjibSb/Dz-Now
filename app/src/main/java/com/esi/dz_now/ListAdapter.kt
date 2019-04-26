package com.esi.dz_now

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val list: List<Article>) : RecyclerView.Adapter<ListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val articleItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ArticleViewHolder(articleItemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = list[position]
        holder.titleText.text = article.title
        holder.contentText.text = article.content
    }

    class ArticleViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var titleText: TextView
        var contentText: TextView
        init {
            titleText= parent.findViewById(R.id.articleTitle)
            contentText= parent.findViewById(R.id.articleContent)
        }
    }

}