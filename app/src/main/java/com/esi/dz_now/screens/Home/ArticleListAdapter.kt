package com.esi.dz_now.screens.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat


class ArticleListAdapter(val list: MutableList<Article>, val context: Context) :
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val articleItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ArticleViewHolder(articleItemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = list[position]
        holder.titleText.text = article.title
        holder.catgoryText.text = context.getString(article.categories.title)
        holder.sourceDateText.text =
            article.source + " | " + article.date//SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(article.date)
        Glide.with(holder.container).load(article.imgUrl).into(holder.image)
//        holder.image.setBackgroundResource(article.img)
        holder.toggleButton.isChecked = article.favorit
        handleClick(holder.container, article.id)
    }

    private fun handleClick(view: View, articleID: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(articleID)
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }

        view.readLaterArticle.setOnClickListener {
            var i = 0
            var article = list[0]
            while (i < list.size) {
                if (list[i].id == articleID) {
                    article = list[i]
                }
                i++
            }
            article.favorit = !article.favorit
        }
    }

    class ArticleViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        var titleText: TextView
        var catgoryText: TextView
        var sourceDateText: TextView
        var image: ImageView
        var container: View
        var toggleButton: ToggleButton

        init {
            titleText = parent.findViewById(R.id.articleTitle)
            catgoryText = parent.findViewById(R.id.articleCategory)
            sourceDateText = parent.findViewById(R.id.articleSourceDate)
            image = parent.findViewById(R.id.articleImage)
            container = parent.findViewById(R.id.itemContainer)
            toggleButton = parent.findViewById(R.id.readLaterArticle)
        }
    }

}