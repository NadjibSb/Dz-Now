package com.esi.dz_now.screens.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.databinding.ListItemBinding
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.list_item.view.*


class ArticleListAdapter:
    RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    private lateinit var articlesList: List<ArticleModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListAdapter.ViewHolder {
        val binding: ListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {
        holder.bind(articlesList[position])
        Log.e("ads list", articlesList.toString())
        handleClick(holder.container, articlesList[position])
        Glide.with(holder.container).load(articlesList[position].img).into(holder.articleImage)
    }

    private fun handleClick(view: View, article: ArticleModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article.id.toInt())
        view.setOnClickListener { v: View ->
            v.findNavController().navigate(action)
        }

        view.readLaterArticle.setOnClickListener {
            article.favoris = !article.favoris
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

   /* class ArticleViewHolder private constructor(parent: View) : RecyclerView.ViewHolder(parent) {
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

        companion object {
            fun creat(parent: ViewGroup): ArticleViewHolder {
                val articleItemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return ArticleViewHolder(articleItemView)
            }
        }
    }*/


    override fun getItemCount(): Int {
        return if (::articlesList.isInitialized) articlesList.size else 0
    }

    fun updateArticlesList(adsList: List<ArticleModel>) {
        this.articlesList = adsList
        notifyDataSetChanged()
    }


}