package com.esi.dz_now.screens.home

import android.content.Context
import android.view.*
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.Categories
import com.esi.dz_now.viewmodel.ArticlesListViewModel
import com.google.android.material.snackbar.Snackbar


class ViewPagerAdapter(
    private val mContext: Context,
    categoriesList: List<Categories>,
    val viewModel: ArticlesListViewModel,
    private val lifeCycleOwner: LifecycleOwner
) : PagerAdapter() {

    private var errorSnackbar: Snackbar? = null
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
        if (header is ViewPagerHeader.CategorieHeader) {
            viewModel.loadArticles(header.categorie.toString())
            viewModel.errorMessage.observe(lifeCycleOwner, Observer { errorMessage ->
                if (errorMessage != null) showError(errorMessage) else hideError()
            })
        }

        setUpRecycleView(layout)
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
        title = if (header is ViewPagerHeader.CategorieHeader) {
            mContext.getString(header.categorie.title)
        } else {
            ""
        }
        return title
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View) {
        var recyclerView = rootView.findViewById(R.id.recycleView) as RecyclerView
        recyclerView.adapter = viewModel.articlesListadapter
        val screenOrientation =
            (mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        when (screenOrientation) {
            Surface.ROTATION_0 -> recyclerView.layoutManager = LinearLayoutManager(mContext)
            else -> recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        }
        recyclerView.setHasFixedSize(true)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }


    private fun getArticlesByCategories(
        categorie: Categories,
        allArticles: List<Article>
    ): MutableList<Article> {
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

    }


}