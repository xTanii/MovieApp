package com.example.tanyanarsinghani.moviebuzz.UI

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.*
import android.widget.Toast
import com.example.tanyanarsinghani.moviebuzz.R
import kotlinx.android.synthetic.main.activity_youtube.*


class YoutubeTrailerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)
        val bundle: Bundle? = intent.extras
        val url = bundle?.getString("url")
        if(url?.isNotEmpty()==true) {
            // var getarticle = DatabaseClient.getInstance(this).getAppDatabase().AsyncDao().getNews(url)


            youtubewebview.getSettings().setJavaScriptEnabled(true)
            youtubewebview.getSettings().setPluginState(WebSettings.PluginState.ON)
           // val mimeType = "text/html";
          //  val encoding = "UTF-8";
            youtubewebview.setWebChromeClient(object : WebChromeClient() {
            })
            youtubewebview.loadUrl(url)

            finish()

        }
        val actionbar=supportActionBar
        actionbar!!.title="MovieBuzz"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }
}

class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url: String = request?.url.toString();
        view?.loadUrl(url)
        return true
    }
    override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
        webView.loadUrl(url)
        return true
    }

    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
    }
}
