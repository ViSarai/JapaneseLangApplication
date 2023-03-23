package br.com.japaneselangapplication.fragmentView;

import static androidx.fragment.app.FragmentManager.TAG;

import static br.com.japaneselangapplication.utils.UTILS.getImageAt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.japaneselangapplication.R;
import br.com.japaneselangapplication.databinding.FragmentNameTranslationBinding;
import br.com.japaneselangapplication.utils.UTILS;

public class NameTranslationFragment extends Fragment {


    private static final String CONST_BASE_URL = "https://madeinjapan.com.br/japantype/katakana.php?romaji=";

    private FragmentNameTranslationBinding binding;

    public NameTranslationFragment() {
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
        binding = FragmentNameTranslationBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        initAction();

        return v;
    }

    private void initAction() {
        binding.btnTranslate.setOnClickListener(v -> {
            String userName = String.valueOf(binding.edtName.getText());
            if(!userName.equals("")) {
                Log.i("string", userName);
                String url = CONST_BASE_URL + userName + "&kana=true";
                getImageAt(getActivity(), url, binding.imgTranslate);
                binding.txtRealName.setText(binding.edtName.getText());
                binding.edtName.setText("");
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        binding.btnTranslate.getWindowToken(), 0);
            }else{
                UTILS.showPopupDialog(getActivity(), -1, "OPA!", "Não esquece do nome no campo, hem!", null);
            }
        });
    }
}