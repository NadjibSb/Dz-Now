package com.esi.dz_now.screens.article


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentArticleBinding
import com.esi.dz_now.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_article.*
import java.text.SimpleDateFormat

class ArticleFragment : Fragment() {

    private lateinit var data: SharedData
    private lateinit var article: Article


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentArticleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article, container, false
        )
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

        articleSourceDate.text =
            article.source + " | " + SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(article.date)
        articleContent.text = article.content + "\n" + article.author
        articleCategory.text = getString(article.categories.title)
        articleImage.setBackgroundResource(article.img)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.read_article_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val addToFavoriteActionMenuItem = menu.findItem(R.id.addToFavoriteAction)
        if (!article.favorit) {
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
            Toast.makeText(context, "Read Mode clicked!", Toast.LENGTH_SHORT).show()
        if (id == R.id.addToFavoriteAction) {
            if (item.title == "stared") {
                item.title = "unstared"
                item.setIcon(R.drawable.ic_menu_star)
                article.favorit = false
            } else {
                item.title = "stared"
                item.setIcon(R.drawable.ic_menu_fullstar)
                article.favorit = true
                Toast.makeText(context, getString(R.string.addToFav), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
