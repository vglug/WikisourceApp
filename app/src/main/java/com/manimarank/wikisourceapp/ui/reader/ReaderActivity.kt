package com.manimarank.wikisourceapp.ui.reader

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.manimarank.wikisourceapp.databinding.ActivityReaderBinding


class ReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.extras?.getString("url") ?: "https://en.m.wikisource.org"

        val hideDivList = listOf("mw-footer", "header-container", "page-actions-menu", "firstHeading", "page-heading", "minerva__tab-container")

        /*val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true*/
        binding.webView.apply {
            initWebSetting(this)
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    showLoader(true)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    // super.onPageFinished(view, url)
                    hideDivList.forEach { divClass ->
                        loadUrl("javascript:(function() { document.getElementsByClassName('${divClass}')[0].style.display='none'; })()")
                    }
                    showLoader(false)
                }
            }
            loadUrl(url)
        }
    }

    private fun showLoader(show: Boolean = true) {
        binding.apply {
            loaderView.visibility = if (show) View.VISIBLE else View.GONE
            webView.visibility = if (!show) View.VISIBLE else View.GONE
        }
    }


    private fun initWebSetting(webView: WebView) {
        val setting = webView.settings
        setting.javaScriptEnabled = true
        //setting.allowFileAccess = true
        //setting.allowFileAccessFromFileURLs = true
        //setting.allowUniversalAccessFromFileURLs = true
        //setting.setAppCacheEnabled(true)
        setting.databaseEnabled = true
        setting.domStorageEnabled = true
        setting.cacheMode = WebSettings.LOAD_DEFAULT
        //setting.setAppCachePath(webView.context.cacheDir.absolutePath)
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        /*setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setting.allowFileAccessFromFileURLs = true
        }*/
    }
}