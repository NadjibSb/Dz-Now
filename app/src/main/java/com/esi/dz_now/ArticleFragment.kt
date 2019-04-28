package com.esi.dz_now


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.esi.dz_now.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentArticleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_article,container,false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.article_fragment_title)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.read_article_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if(id==R.id.shareAction)
            Toast.makeText(context, "Share clicked!", Toast.LENGTH_SHORT).show()
        if(id==R.id.readModeAction)
            Toast.makeText(context, "Read Mode clicked!", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }



}
