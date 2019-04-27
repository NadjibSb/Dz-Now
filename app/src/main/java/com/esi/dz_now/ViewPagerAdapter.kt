package com.esi.dz_now

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter


class ViewPagerAdapter(private val mContext: Context) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val customPagerEnum = Categories.values()[position]
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.viewpager_content, collection, false) as ViewGroup
        val title = layout.findViewById<TextView>(R.id.title)
        if(position%2==0){
            setUpRecycleView(layout,defaultList(8))
        }else{
            setUpRecycleView(layout,defaultList2(8))
        }


        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return Categories.values().size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val customPagerEnum = Categories.values()[position]
        return mContext.getString(customPagerEnum.title)
    }
    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: List<Article>) {
        var recyclerView = rootView.findViewById(R.id.recycleView) as RecyclerView
        recyclerView.adapter = ListAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.setHasFixedSize(true)
    }

    private fun defaultList(size:Int):ArrayList<Article>{
        var articles = ArrayList<Article>()
        for(i in 0..size){
            articles.add(Article("article $i","","article $i, article $i, article $i, article $i, "))
        }
        return articles
    }
    private fun defaultList2(size:Int):ArrayList<Article>{
        var articles = ArrayList<Article>()
        for(i in 0..size){
            articles.add(Article("ARTICLE $i","","article $i, article $i, article $i, article $i, "))
        }
        return articles
    }

}