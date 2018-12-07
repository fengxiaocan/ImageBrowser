package com.app.baselib.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.app.baselib.R;
import com.app.baselib.ui.WebActivity;

/**
 * @项目名： MiParkAdmin
 * @包名： com.fzpark.parkadmin.ui.acty
 * @创建者: Noah.冯
 * @时间: 17:07
 * @描述： 展示html文本的web
 */

public class HtmlTextActivity extends WebActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getBundle();
        if (bundle != null) {
            String html = bundle.getString("html");
            loadHtmlText(html);
        }
    }

    @Override
    public int createContentView() {
        return R.layout.activity_html;
    }

    @Override
    public WebView findWebView() {
        return (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
