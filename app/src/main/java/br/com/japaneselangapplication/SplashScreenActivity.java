package br.com.japaneselangapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import br.com.japaneselangapplication.databinding.FragmentHomeBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private FragmentHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        verifyConnection();
    }

    private void verifyConnection() {
        binding.contentRetry.setVisibility(View.GONE);
        binding.progress.setVisibility(View.VISIBLE);
        if (isNetworkAvailable(this)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.progress.setVisibility(View.GONE);
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }, 5000);

        } else {
            CallNoConnectionWarning();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected()));
    }

    private void CallNoConnectionWarning() {
       binding.contentRetry.setVisibility(View.VISIBLE);
       binding.progress.setVisibility(View.GONE);
       binding.retry.setOnClickListener(v -> {
           verifyConnection();
       });
    }
}