package com.esi.dz_now.screens.favorit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.databinding.ListItemBinding
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.viewmodel.ArticleViewModel


class FavorisArticleListAdapter :
    RecyclerView.Adapter<FavorisArticleListAdapter.ViewHolder>() {

    private lateinit var articlesList: List<ArticleModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articlesList[position])
        handleClick(holder.container, articlesList[position])
        Glide.with(holder.container).load(articlesList[position].img).into(holder.articleImage)
    }

    private fun handleClick(view: View, article: ArticleModel) {
        Log.e("Article", article.content)
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToArticleFragment(
            articleID = article.id,
            articleDate = article.date,
            articleImg = article.img,
            articleSource = article.source,
            articleTitle = article.title,
            articleUrl = article.url,
            articleCategory = article.category,
            articleContent = article.content
        )
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ArticleViewModel()
        val container: View = binding.itemContainer
        val articleImage: ImageView = binding.articleImage

        fun bind(article: ArticleModel) {

            viewModel.bind(article)
            binding.viewModel = viewModel
        }


    }


    override fun getItemCount(): Int {
        return if (::articlesList.isInitialized) articlesList.size else 0
    }

    fun updateArticlesList(articlesList: List<ArticleModel>) {
        this.articlesList = articlesList
        notifyDataSetChanged()
    }
}