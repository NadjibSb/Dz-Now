package com.esi.dz_now.screens.Home

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.Categories

const val HOME_SOURCE="settings"


class ViewPagerAdapter(
    private val mContext: Context,
    private val articlesList: MutableList<Article>,
    private val categoriesList: List<Categories>
) : PagerAdapter() {

    private lateinit var mCategoriesList: MutableList<ViewPagerHeader>

    init {
        mCategoriesList = initializeList(categoriesList)
    }

    private fun initializeList(categories: List<Categories>): MutableList<ViewPagerHeader> {
        var list = mutableListOf<ViewPagerHeader>()
        list.add(ViewPagerHeader.AllHeader())
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
            getarticlesByCategories(header.categorie, articlesList)
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
        recyclerView.adapter = ArticleListAdapter(list, mContext, HOME_SOURCE)
        val screenOrientation =
            (mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        when (screenOrientation) {
            Surface.ROTATION_0 -> recyclerView.layoutManager = LinearLayoutManager(mContext)
            else -> recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        }
        recyclerView.setHasFixedSize(true)
    }

    fun getarticlesByCategories(categorie: Categories, allArticles: List<Article>): MutableList<Article> {
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


}