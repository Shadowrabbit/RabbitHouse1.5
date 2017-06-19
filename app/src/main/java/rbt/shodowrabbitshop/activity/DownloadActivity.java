package rbt.shodowrabbitshop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.base.BaseActivity;

public class DownloadActivity extends BaseActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        init();
    }
    private void init() {
        setTitle("更新地址");
        getSupportActionBar().show();
        webView= (WebView) findViewById(R.id.download);
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setBuiltInZoomControls(true);  //启用内置缩放装置
        settings.setJavaScriptEnabled(true);    //启用JS脚本
//        webView.setWebViewClient(new WebViewClient() {
//            //当点击链接时,希望覆盖而不是打开新窗口
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);  //加载新的url
//                return true;    //返回true,代表事件已处理,事件流到此终止
//            }
//        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//                        webView.goBack();   //后退
//                        return true;    //已处理
//                    }
//                }
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            //当WebView进度改变时更新窗口进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //Activity的进度范围在0到10000之间,所以这里要乘以100
                DownloadActivity.this.setProgress(newProgress * 100);
            }
        });
        webView.loadUrl("https://www.pgyer.com/YOZb");
    }
}
