package br.com.japaneselangapplication.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.japaneselangapplication.databinding.AdapterCardGreetingsBinding;


public class AdapterGreat extends RecyclerView.Adapter<AdapterGreat.ViewHolder> {

    private Activity activity;
    private Listener listener;
    AdapterCardGreetingsBinding binding;


    private ArrayList<String> audio;
    private ArrayList<String> greating;
    private ArrayList<String> greatingPtBr;



    public AdapterGreat(ArrayList<String> audio, ArrayList<String> greating, ArrayList<String> greatingPtBr, Listener listener) {
        this.audio = audio;
        this.greating = greating;
        this.greatingPtBr = greatingPtBr;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = AdapterCardGreetingsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        binding.greatRomaji.setText(greatingPtBr.get(position));
        binding.greatHiragana.setText(greating.get(position));

        binding.btnPlay.setOnClickListener(view -> {
            listener.onClick(audio.get(position));
        });
        String string = greatingPtBr.get(position);
        if(string.contains("(")){
            binding.content.setOnClickListener(view -> {
                listener.onClick2(greatingPtBr.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        return greating.size();
    }

    public interface Listener {

        void onClick(String audio);
        void onClick2(String greatingPtBr);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        AdapterCardGreetingsBinding binding;

        public ViewHolder(@NonNull AdapterCardGreetingsBinding bindingViewHolder) {
            super(bindingViewHolder.getRoot());

            binding = bindingViewHolder;

        }
    }
}
