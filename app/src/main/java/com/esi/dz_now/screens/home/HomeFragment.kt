package com.esi.dz_now.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.Categories
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentHomeBinding
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.screens.MainActivity
import com.esi.dz_now.viewmodel.HomeFragmentViewModel
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.text.Typography.quote


class HomeFragment : Fragment() {

    private lateinit var data: SharedData

    // Lazy Inject ViewModel
    private val viewModel: HomeFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        data = this.activity as SharedData
        (activity as MainActivity).supportActionBar?.title = getString(R.string.home_fragment_title)

        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(context!!, getData(true), data.getCategories())
        binding.slidingTabs.setupWithViewPager(viewPager)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun getData(refresh : Boolean):MutableList<Article> {
        var list_articles = mutableListOf<Article>()
        viewModel.getArticleData(refresh).observe(this, Observer {
            if(it == null){
                logInfo("Handle Error")
            }
            if(it?.error == null){
                if(it?.code==null) {
                    val articles: List<ArticleModel>? = it!!.posts

                    var article_data: Article?
                    for (article in articles!!) {
                        Log.e("errot", ""+article.toString())
                        article_data = Article(
                            article.id.toInt(),
                            article.title,
                            1,
                            "",
                            Categories.POLITICS,
                            Date(),
                            false,
                            "ElKhabar",
                            "",
                            article.img,
                            article.category,
                            article.date,
                            article.url
                            )
                        list_articles.add(article_data)
                    }
                }else{
                    when(it.code!!){
                        404 -> toast("Sorry not found! :(")
                        else ->{
                            toast("Error! Please try again..")
                        }
                    }
                }
            }else{
                val e : Throwable = it.error!!
                logInfo("Error is " + e.message)
            }
        })
        return list_articles
    }

    private fun logInfo(msg: String){
        i("MainActivity", msg)
    }

    private fun toast(msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }




}