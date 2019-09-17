package com.esi.dz_now.screens.article


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentArticleBinding
import com.esi.dz_now.injection.ViewModelFactory
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.screens.article.ArticleFragmentArgs
import com.esi.dz_now.screens.MainActivity
import com.esi.dz_now.viewmodel.ArticleViewModel
import com.esi.dz_now.viewmodel.SavedArticlesListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*
import java.text.SimpleDateFormat

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private var errorSnackbar: Snackbar? = null
    private lateinit var article: ArticleModel
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article, container, false
        )
        (activity as MainActivity).supportActionBar?.title = getString(com.esi.dz_now.R.string.article_fragment_title)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        binding.viewModel = viewModel


        if(isOnline(context!!)){
            viewModel.loadArticleContent(ArticleFragmentArgs.fromBundle(arguments!!).articleSource, ArticleFragmentArgs.fromBundle(arguments!!).articleUrl)
            viewModel.errorMessage.observe(this, Observer {
                //errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
            })
        }



        article= ArticleModel(
            ArticleFragmentArgs.fromBundle(arguments!!).articleID,
            ArticleFragmentArgs.fromBundle(arguments!!).articleTitle,
            ArticleFragmentArgs.fromBundle(arguments!!).articleUrl,
            ArticleFragmentArgs.fromBundle(arguments!!).articleContent,
            ArticleFragmentArgs.fromBundle(arguments!!).articleImg,
            ArticleFragmentArgs.fromBundle(arguments!!).articleCategory,
            ArticleFragmentArgs.fromBundle(arguments!!).articleDate,
            ArticleFragmentArgs.fromBundle(arguments!!).articleSource)
        viewModel.bind(article)
        return binding.root
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context!!).load(article.img).into(articleImage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.read_article_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val addToFavoriteActionMenuItem = menu.findItem(R.id.addToFavoriteAction)
        if (!article.favoris) {
            addToFavoriteActionMenuItem.setIcon(R.drawable.ic_menu_star)
            addToFavoriteActionMenuItem.title = "unstared"
        } else {
            addToFavoriteActionMenuItem.setIcon(R.drawable.ic_menu_fullstar)
            addToFavoriteActionMenuItem.title = "stared"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if (id == R.id.shareAction) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, article.title)
                .putExtra(Intent.EXTRA_TEXT, article.content)

            startActivity(Intent.createChooser(shareIntent, "Partager Article"))
        }
        if (id == R.id.readModeAction)
            Toast.makeText(context, "Voice Mode clicked!", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).speakOut(viewModel.getArticleTitle().value+ " "+ viewModel.getArticleContent().value)

        if (id == R.id.addToFavoriteAction) {

            if (item.title == "stared") {
                item.title = "unstared"
                item.setIcon(R.drawable.ic_menu_star)

                article.favoris = false
            } else {
                item.title = "stared"
                item.setIcon(R.drawable.ic_menu_fullstar)
                article.favoris = true
                article.content=viewModel.getArticleContent().value!!
                var viewModel_save: SavedArticlesListViewModel = ViewModelProviders.of(this, ViewModelFactory(activity!! as AppCompatActivity)).get(SavedArticlesListViewModel::class.java)
                viewModel_save.saveArticle(article)
                viewModel.errorMessage.observe(this, Observer {
                        errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
                })

                Toast.makeText(context, getString(R.string.addToFav), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }




}
