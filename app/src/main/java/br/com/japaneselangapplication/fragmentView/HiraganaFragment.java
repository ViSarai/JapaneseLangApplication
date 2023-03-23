package br.com.japaneselangapplication.fragmentView;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

import br.com.japaneselangapplication.R;
import br.com.japaneselangapplication.databinding.FragmentHiraganaBinding;

public class HiraganaFragment extends Fragment {

    private FragmentHiraganaBinding binding;

    public HiraganaFragment() {
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
        binding = FragmentHiraganaBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        return v;

    }

}