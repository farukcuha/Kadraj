package com.pandorina.kadraj.Web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;


public class WebViewProvider extends Activity {
    View view;
    String pdTitle, loadUrl;
    int webViewIdTag, progressIdTag;
    WebView webView;
    RoundedHorizontalProgressBar progressBar;

    public WebViewProvider(View view, String pdTitle, String loadUrl, int webViewIdTag, int progressIdTag) {
        this.view = view;
        this.pdTitle = pdTitle;
        this.loadUrl = loadUrl;
        this.webViewIdTag = webViewIdTag;
        this.progressIdTag = progressIdTag;
    }

    @SuppressLint("SetJavaScriptEnabled")
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

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }
        });
    }

    private Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };

    private void webViewGoBack(){
        webView.goBack();
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
