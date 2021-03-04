package com.example.kadraj.Web;

import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;


public class WebViewProvider extends Activity {
    private View view;
    private String pdTitle, loadUrl;
    private int webViewIdTag, progressIdTag;
    private WebView webView;
    private RoundedHorizontalProgressBar progressBar;

    public WebViewProvider(View view, String pdTitle, String loadUrl, int webViewIdTag, int progressIdTag) {
        this.view = view;
        this.pdTitle = pdTitle;
        this.loadUrl = loadUrl;
        this.webViewIdTag = webViewIdTag;
        this.progressIdTag = progressIdTag;
    }

    public void provider(){
        webView = view.findViewById(webViewIdTag);
        progressBar = view.findViewById(progressIdTag);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        new AdBlockerWebView.init(view.getContext()).initializeWebView(webView);
        webView.setWebViewClient(new Browser_home());

        webView.loadUrl(loadUrl);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.animateProgress(100, 0, newProgress);
                progressBar.setProgress(newProgress);


            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class Browser_home extends WebViewClient {

        Browser_home() {}

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            return AdBlockerWebView.blockAds(view,url) ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);

        }
    }


}
