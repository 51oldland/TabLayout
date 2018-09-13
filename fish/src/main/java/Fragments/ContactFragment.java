package Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.www.fish.R;

public class ContactFragment extends Fragment implements View.OnClickListener {
    View mView;
    TextView mContact;
    WebView mWebView;
    String mURL = "";

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_contact, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mContact = mView.findViewById(R.id.bt);
        mContact.setOnClickListener(this);
        mURL = "http://wpa.qq.com/msgrd?v=3&uin=3510361326&site=qq&menu=yes";
        mWebView = new WebView(getActivity());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            //重写该方法就是为了在使用WEBVIEW请求网页时，先拦截请求的数据，可以在请求网络数据前做一些处理
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //这里处理为了就是让一般的http和https协议开头的走正常的页面，而其他的URL则会开启一个Acitity然后去调用原生APP应用
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return null;
                }
            }
        });
    }

    //3510361326    密码:qq13230405244
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                mWebView.loadUrl(mURL);
                break;
        }
    }
}
