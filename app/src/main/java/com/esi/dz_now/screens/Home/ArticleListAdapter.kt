package com.esi.dz_now.screens.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.data.Article

class ArticleListAdapter(val list: MutableList<Article>, val context: Context) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val articleItemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ArticleViewHolder(articleItemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = list[position]
        holder.titleText.text = article.title
        holder.contentText.text = article.content
        handleClick(holder.container,article.id)
        Glide.with(context).load(article.img).to(holder.image)
    }

    private fun handleClick(view: View, articleID: Int){
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(articleID)
        view.setOnClickListener {v:View ->
            v.findNavController().navigate(action)
        }
    }

    class ArticleViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var titleText: TextView
        var contentText: TextView
        var image: ImageView
        var container: View
        init {
            titleText= parent.findViewById(R.id.articleTitle)
            contentText= parent.findViewById(R.id.articleContent)
            image= parent.findViewById(R.id.articleImage)
            container= parent.findViewById(R.id.itemContainer)
        }
    }

}