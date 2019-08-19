package com.esi.dz_now.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.Categories
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.viewmodel.HomeFragmentViewModel
import java.util.*

import androidx.lifecycle.Observer



class ViewPagerAdapter(
    private val mContext: Context,
    private val articlesList: MutableList<Article>,
    categoriesList: List<Categories>,
    val viewModel: HomeFragmentViewModel,
    val owner: LifecycleOwner
) : PagerAdapter() {

    private var mCategoriesList: MutableList<ViewPagerHeader>

    init {
        mCategoriesList = initializeList(categoriesList)
    }

    //setup the 'All' tab with the categories tabs
    private fun initializeList(categories: List<Categories>): MutableList<ViewPagerHeader> {
        var list = mutableListOf<ViewPagerHeader>()
        //list.add(ViewPagerHeader.AllHeader())
        for (cat in categories) {
            list.add(ViewPagerHeader.CategorieHeader(cat))
        }
        return list
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val header = mCategoriesList[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.viewpager_content, collection, false) as ViewGroup

        //to check if it's a Categorie tab or the 'All' tab
        val list: MutableList<Article> = if (header is ViewPagerHeader.CategorieHeader) {
            getArticleByCategory(true, header.categorie)
        } else {//in case it's the 'All' tab, pass the list of all articles
            articlesList
        }
        setUpRecycleView(layout, list)
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return mCategoriesList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title = ""
        val header = mCategoriesList[position]
        title = if (header is ViewPagerHeader.AllHeader) {
            mContext.getString(header.text)
        } else if (header is ViewPagerHeader.CategorieHeader) {
            mContext.getString(header.categorie.title)
        } else {
            ""
        }
        return title
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: MutableList<Article>) {
        var recyclerView = rootView.findViewById(R.id.recycleView) as RecyclerView
        recyclerView.adapter = ArticleListAdapter(list, mContext)
        val screenOrientation =
            (mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        when (screenOrientation) {
            Surface.ROTATION_0 -> recyclerView.layoutManager = LinearLayoutManager(mContext)
            else -> recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        }
        recyclerView.setHasFixedSize(true)
    }

    private fun getArticlesByCategories(categorie: Categories, allArticles: List<Article>): MutableList<Article> {
        var newList = mutableListOf<Article>()
        for (article in allArticles) {
            if (article.categories == categorie) {
                newList.add(article)
            }
        }
        return newList
    }

    sealed class ViewPagerHeader {
        data class CategorieHeader(val categorie: Categories) : ViewPagerHeader()
        data class AllHeader(val text: Int = R.string.all_category) : ViewPagerHeader()
    }

    @SuppressLint("SetTextI18n")
    private fun getArticleByCategory(refresh : Boolean, category: Categories):MutableList<Article> {
        var list_articles = mutableListOf<Article>()
        viewModel.getArticleDataByCategory(refresh, category.name).observe(owner, Observer {
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
                            category,
                            Date(),
                            false,
                            "Elwatan",
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
        Log.i("MainActivity", msg)
    }

    private fun toast(msg: String){
        //Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }



}