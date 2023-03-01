package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
         webView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
             updateScrollIndicator(scrollY)
         }
    }
    private fun updateScrollIndicator(scrollY: Int) {
        val screenHeight = webView.height
        val contentHeight = webView.contentHeight * webView.scale
        val scrollExtent = screenHeight.toDouble() / contentHeight.toDouble()

        val indicatorWidth = scroll_indicator.width / scroll_indicator.childCount

        if (webView.progress < 100) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }

        for (i in 0 until scroll_indicator.childCount) {
            val indicator = scroll_indicator.getChildAt(i)
            val offset = (i * indicatorWidth).toDouble()
            val position = (scrollY.toDouble() / contentHeight.toDouble()) * scroll_indicator.width.toDouble()

            if (position >= offset && position <= offset + indicatorWidth) {
                indicator.setBackgroundResource(R.drawable.heading_bg)
            } else {
                indicator.setBackgroundResource(R.drawable.search_bar_background)
            }
        }
    }

}