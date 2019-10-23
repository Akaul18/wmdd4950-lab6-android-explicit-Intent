package com.example.lab6explicitintent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RedditActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redditactivity);

        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                Log.d("WEBVIEW", "Finished Loading "+url);
            }
        });
        webView.clearCache(true);

        Intent intent = getIntent();
        if(intent != null){
            String url = intent.getStringExtra("url");
            webView.loadUrl(url);
//            Log.d("url:", str);
        }

    }
}
