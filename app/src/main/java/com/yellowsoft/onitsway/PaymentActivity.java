package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 22-04-2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by sriven on 12/28/2015.
 */
public class PaymentActivity extends Activity {
    String pack_id,pack_price;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.payment_screen);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "app");
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new MyWebViewClient());
        String cust_id = getIntent().getStringExtra("cust_id");
        pack_price = getIntent().getStringExtra("total_price");
        webView.loadUrl(Settings.PAYMENT_URL + "cust_id=" + cust_id + "&amount=" + pack_price);
        Log.e("pay_url", Settings.PAYMENT_URL + "cust_id=" + cust_id + "&amount=" + pack_price);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);
        progress.setProgress(0);

    }
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void send_message(String toast,Boolean success) {
            Log.e("toast",toast);
            if(toast.equals("success")){
                Toast.makeText(mContext, Settings.getword(PaymentActivity.this, "payment_succ"), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra("msg","OK");
                intent.putExtra("pack_id",pack_id);
                setResult(7, intent);
                finish();
            }

            else{
                Toast.makeText(mContext, Settings.getword(PaymentActivity.this, "pay_failed"), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra("msg","NotOK");
                intent.putExtra("pack_id",pack_id);
                setResult(7, intent);
                finish();
            }
        }
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            PaymentActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    @Override
    public void onBackPressed() {
        // your code.
        Toast.makeText(PaymentActivity.this, Settings.getword(PaymentActivity.this, "pay_failed"), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("msg","NotOK");
        intent.putExtra("pack_id",pack_id);
        setResult(7, intent);
        finish();

    }

}