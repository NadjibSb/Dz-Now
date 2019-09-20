package com.esi.dz_now.screens.article


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.databinding.FragmentArticleBinding
import com.esi.dz_now.injection.ViewModelFactory
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.screens.MainActivity
import com.esi.dz_now.screens.dialog.ShareDialog
import com.esi.dz_now.viewmodel.ArticleViewModel
import com.esi.dz_now.viewmodel.SavedArticlesListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*


const val SHARE_CODE = 13

class ArticleFragment : Fragment() {
    private val TAG = "TAG-ArticleFragment"
    private lateinit var binding: FragmentArticleBinding
    private var errorSnackbar: Snackbar? = null
    private lateinit var article: ArticleModel
    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article, container, false
        )
        (activity as MainActivity).supportActionBar?.title =
            getString(com.esi.dz_now.R.string.article_fragment_title)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        binding.viewModel = viewModel


        if (isOnline(context!!)) {
            viewModel.loadArticleContent(
                ArticleFragmentArgs.fromBundle(arguments!!).articleSource,
                ArticleFragmentArgs.fromBundle(arguments!!).articleUrl
            )
            viewModel.errorMessage.observe(this, Observer {
                //errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
            })
        }

        article = ArticleModel(
            ArticleFragmentArgs.fromBundle(arguments!!).articleID,
            ArticleFragmentArgs.fromBundle(arguments!!).articleTitle,
            ArticleFragmentArgs.fromBundle(arguments!!).articleUrl,
            ArticleFragmentArgs.fromBundle(arguments!!).articleContent,
            ArticleFragmentArgs.fromBundle(arguments!!).articleImg,
            ArticleFragmentArgs.fromBundle(arguments!!).articleCategory,
            ArticleFragmentArgs.fromBundle(arguments!!).articleDate,
            ArticleFragmentArgs.fromBundle(arguments!!).articleSource
        )
        viewModel.bind(article)
        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
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
            addToFavoriteActionMenuItem.setIcon(R.drawable.ic_bookmark_border)
            addToFavoriteActionMenuItem.title = "unstared"
        } else {
            addToFavoriteActionMenuItem.setIcon(R.drawable.ic_bookmark)
            addToFavoriteActionMenuItem.title = "stared"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId
        if (id == R.id.shareAction) {
            shareArticle()
        }
        if (id == R.id.readModeAction) {
            Toast.makeText(context, "Voice Mode clicked!", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).speakOut(viewModel.getArticleTitle().value + ". " + viewModel.getArticleContent().value)
        }
        if (id == R.id.addToFavoriteAction) {

            if (item.title == "stared") {
                item.title = "unstared"
                item.setIcon(R.drawable.ic_bookmark_border)

                article.favoris = false
            } else {
                item.title = "stared"
                item.setIcon(R.drawable.ic_bookmark)
                article.favoris = true
                article.content = viewModel.getArticleContent().value!!
                var viewModel_save: SavedArticlesListViewModel =
                    ViewModelProviders.of(this, ViewModelFactory(activity!! as AppCompatActivity))
                        .get(SavedArticlesListViewModel::class.java)
                viewModel_save.saveArticle(article)
                viewModel.errorMessage.observe(this, Observer { errorMessage ->
                    if (errorMessage != null) showError(errorMessage) else hideError()
                })
                viewModel_save.saveArticleOnline("", article)
                viewModel.errorMessage.observe(this, Observer { errorMessage ->
                    if (errorMessage != null) showError(errorMessage) else hideError()
                })

                Toast.makeText(context, getString(R.string.addToFav), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).stopTts()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).stopTts()
    }

    private fun shareArticle() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, SHARE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SHARE_CODE) {
            if (resultCode == RESULT_OK) {
                val id: String
                val name: String
                val hasPhoneNumber: String
                var num = ""
                var email = ""

                val contact = data?.data
                val curseur = context!!.contentResolver.query(contact, null, null, null, null)
                curseur.moveToFirst()

                //selected contact
                id = curseur.getString(curseur.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                name =
                    curseur.getString(curseur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                Log.i(TAG, "Name: $name")

                //Phone number
                hasPhoneNumber =
                    curseur.getString(curseur.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                if (hasPhoneNumber == "1") {
                    val phones = context!!.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
                    )
                    phones.moveToFirst()
                    num = phones.getString(phones.getColumnIndex("data1"))
                    Log.i(TAG, "Num: $num")
                }
                //email
                val emails = context!!.contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null
                )
                if (emails.count > 0) {
                    emails.moveToFirst()
                    email =
                        emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                    if (email != null) {
                        Log.i(TAG, "email: $email")
                    }
                }
                val dialog =
                    ShareDialog(email, num, article.title, viewModel.getArticleContent().value)

                dialog?.show(fragmentManager!!, "SHARE")

            }
        }
    }
}
