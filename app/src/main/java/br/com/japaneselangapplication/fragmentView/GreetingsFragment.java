package br.com.japaneselangapplication.fragmentView;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.japaneselangapplication.R;
import br.com.japaneselangapplication.adapter.AdapterGreat;

import br.com.japaneselangapplication.databinding.FragmentGreetingsBinding;
import br.com.japaneselangapplication.utils.UTILS;

public class GreetingsFragment extends Fragment {

    FragmentGreetingsBinding binding;
    MediaPlayer mediaPlayer;
    AdapterGreat adapterGreat;
    String url, greatingPtBtUrl;
    int id;


    public GreetingsFragment() {
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

        binding = FragmentGreetingsBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        initActions();

        return v;
    }

    private void initActions() {

        ArrayList<String> audio = new ArrayList<>();
        audio.addAll(Arrays.asList(getResources().getStringArray(R.array.audios)));

        ArrayList<String> greatings = new ArrayList<>();
        greatings.addAll(Arrays.asList(getResources().getStringArray(R.array.greatings)));

        ArrayList<String> greatings_PT_BR = new ArrayList<>();
        greatings_PT_BR.addAll(Arrays.asList(getResources().getStringArray(R.array.greatings_PT_BR)));

        adapterGreat = new AdapterGreat(audio, greatings, greatings_PT_BR, new AdapterGreat.Listener() {
            @Override
            public void onClick(String audio) {
                url = audio;
                playAudio();
            }

            @Override
            public void onClick2(String greatingPtBr) {
                greatingPtBtUrl = greatingPtBr;
                verifyDialog();
                showDialog();
            }
        });
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recycler.setAdapter(adapterGreat);

    }

    private void showDialog() {

        switch (id) {
            case 1:
                UTILS.showPopupDialog(getActivity(), -1, "", "(1) Konbanwa e Konnichiwa se escrevem com は, pois nesse caso é a partícula WA (que se escreve com o hiragana HA – como vimos anteriormente).", null);
                break;
            case 2:
                UTILS.showPopupDialog(getActivity(), -1, "", "(2) Oyasuminasai é usado como um boa noite de despedida, usa-se quando se está indo embora ou indo dormir…", null);
                break;
            case 3:
                UTILS.showPopupDialog(getActivity(), -1, "", "(3) Sayounara é uma despedida usada para quando você ficará algum tempo sem ver a pessoa. Se você verá no dia seguinte, é melhor usar mata ashita.", null);
                break;
            case 4:
                UTILS.showPopupDialog(getActivity(), -1, "", "(4) Ikaga desu ka significa ‘como vai?’. Uma outra forma é perguntar Ogenki desu ka, onde ‘genki’ significa bem ou saudável. Só use essa forma se você souber que a pessoa está bem… Você não perguntaria para uma pessoa ‘Tudo bem?’ se você sabe que ela está mal.", null);
                break;
        }
    }

    private void verifyDialog() {

        if (greatingPtBtUrl.contains("1")) {
            id = 1;
        } else if (greatingPtBtUrl.contains("2")) {
            id = 2;
        } else if (greatingPtBtUrl.contains("3")) {
            id = 3;
        } else if (greatingPtBtUrl.contains("4")) {
            id = 4;
        }

    }

    private void playAudio() {

        String audioUrl = url;


        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();

            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}