package com.esi.dz_now.screens.Article


import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentArticleBinding
import com.esi.dz_now.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_article.*
import android.R
import android.content.Intent
import com.esi.dz_now.data.Article

class ArticleFragment : Fragment() {

    private lateinit var data: SharedData
    private lateinit var article: Article


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentArticleBinding = DataBindingUtil.inflate(inflater,
            com.esi.dz_now.R.layout.fragment_article,container,false)
        (activity as MainActivity).supportActionBar?.title = getString(com.esi.dz_now.R.string.article_fragment_title)
        setHasOptionsMenu(true)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleID = ArticleFragmentArgs.fromBundle(arguments!!).articleID
        data = activity as SharedData
        article = data.getArticleById(articleID)
        articleTitle.text = article.title

        articleSourceDate.text = article.source+"|"+article.date.toString()
        articleContent.text = article.content
        articleCategory.text = article.categories.name
        articleImage.setBackgroundResource(com.esi.dz_now.R.drawable.article_image)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.esi.dz_now.R.menu.read_article_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val addToFavoriteActionMenuItem = menu.findItem(com.esi.dz_now.R.id.addToFavoriteAction)
        if(!article.favorit)
        {
            addToFavoriteActionMenuItem.setIcon(com.esi.dz_now.R.drawable.star_border)
            addToFavoriteActionMenuItem.title = "unstared"
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if(id== com.esi.dz_now.R.id.shareAction)
        {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent,"Partager Article"))
        }
        if(id== com.esi.dz_now.R.id.readModeAction)
            Toast.makeText(context, "Read Mode clicked!", Toast.LENGTH_SHORT).show()
        if(id== com.esi.dz_now.R.id.addToFavoriteAction)
        {
            if(item.title=="stared")
            {
                item.title="unstared"
                item.setIcon(com.esi.dz_now.R.drawable.star_border)
                article.favorit = true
            }
            else
            {
                item.title="stared"
                item.setIcon(com.esi.dz_now.R.drawable.round_star)
                article.favorit = false
            }
        }



        return super.onOptionsItemSelected(item)
    }





}
