package com.fishinwater.fiwentertainmentstar;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView {

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WebSettings mWebSettings = getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        //设置解码格式
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        //支持js 特效
        mWebSettings.setJavaScriptEnabled(true);
        // 使用 Cookie 保存密码
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        CookieManager mCookieManager = CookieManager.getInstance();
        mCookieManager.setAcceptCookie(true);
        mCookieManager.acceptThirdPartyCookies(this);

    }



}
