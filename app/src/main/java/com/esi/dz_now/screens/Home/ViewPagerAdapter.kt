package com.esi.dz_now.screens.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.Categories


class ViewPagerAdapter(
    private val mContext: Context,
    private val articlesList: MutableList<Article>,
    private val categoriesList: List<Categories>
) : PagerAdapter() {

    private lateinit var mCategoriesList : MutableList<ViewPagerHeader>

    init {
        articlesList.shuffle()
        mCategoriesList = initializeList(categoriesList)
    }

    private fun initializeList(categories: List<Categories>): MutableList<ViewPagerHeader> {
        var list =mutableListOf<ViewPagerHeader>()
        list.add(ViewPagerHeader.AllHeader())
        for (cat in categories){
            list.add(ViewPagerHeader.CategorieHeader(cat))
        }
        return list
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val header = mCategoriesList[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.viewpager_content, collection, false) as ViewGroup
        //val title = layout.findViewById<TextView>(R.id.title)

        if (header is ViewPagerHeader.AllHeader){
            setUpRecycleView(layout,articlesList)
        }else if(header is ViewPagerHeader.CategorieHeader){
            setUpRecycleView(layout, getarticlesByCategories(header.categorie,articlesList))
        }

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
        var title =""
        val header = mCategoriesList[position]
        if (header is ViewPagerHeader.AllHeader){
            title= mContext.getString(header.text)
        }else if(header is ViewPagerHeader.CategorieHeader){
            title= mContext.getString(header.categorie.title)
        }
        return title
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: MutableList<Article>) {
        var recyclerView = rootView.findViewById(R.id.recycleView) as RecyclerView
        recyclerView.adapter = ArticleListAdapter(list, mContext)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.setHasFixedSize(true)
    }

    fun getarticlesByCategories(categorie: Categories, allArticles: List<Article>):MutableList<Article>{
        var newList = mutableListOf<Article>()
        for (article in allArticles){
            if (article.categories==categorie){
                newList.add(article)
            }
        }
        return newList
    }

    sealed class ViewPagerHeader{
        data class CategorieHeader(val categorie: Categories):ViewPagerHeader()
        data class AllHeader(val text: Int = R.string.all_category ):ViewPagerHeader()
    }


}