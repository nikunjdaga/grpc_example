package com.nikunj.gamezopinterview.ui.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nikunj.gamezopinterview.R;
import com.nikunj.gamezopinterview.ui.main.MainActivity;
import com.nikunj.gamezopinterview.utils.DownloadConstants;

public class WebviewActivity extends AppCompatActivity {
    private WebView webview;
    private ProgressBar progressBar;
    private String http = "http://";
    private String https = "https://";
    private String www =  "www.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //no title bar

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_webview);

        webview = (WebView) findViewById(R.id.webView);
        String getPath = getIntent().getStringExtra("path");

        setWebView();

        if (getPath != null) {
            String urlString = DownloadConstants.FILE_PATH + getPath
                    + DownloadConstants.HTML_FILE_NAME;
            webview.loadUrl(urlString);
        }

    }

    private void setWebView() {
        WebChromeClient webClient = new WebChromeClient() {
        };
        webview.setWebChromeClient(webClient);
        WebSettings settings = webview.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webview.canGoBack()) {
            webview.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}
