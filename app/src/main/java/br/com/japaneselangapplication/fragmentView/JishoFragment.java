package br.com.japaneselangapplication.fragmentView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import br.com.japaneselangapplication.R;
import br.com.japaneselangapplication.databinding.FragmentJishoBinding;

public class JishoFragment extends Fragment {

    FragmentJishoBinding binding;


    public JishoFragment() {
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
        binding = FragmentJishoBinding.inflate(inflater,container, false);

        View v = binding.getRoot();

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDefaultTextEncodingName("utf-8");
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.setWebChromeClient(new WebChromeClient());

        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setDatabaseEnabled(true);
        binding.webView.loadUrl("https://pt.glosbe.com/pt/ja");

        return v;

    }

}