package coop.biantik.traductor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import coop.biantik.traductor.activities.BaseActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class WebViewFragment extends Fragment {

    public WebViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WebView webview = new WebView(getActivity());
        webview.setWebViewClient(new WebViewClient());

        String url = getActivity().getIntent().getStringExtra("url");
        webview.loadUrl(url);
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(url);
        return webview;
    }
}
