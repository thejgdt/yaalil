package com.thejgdt.yaalil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000; // Durasi splash screen dalam milidetik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Memuat tata letak splash screen

        // Mengatur penundaan untuk memperlihatkan splash screen selama beberapa detik
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setelah waktu penundaan berakhir, buka MainActivity dan tutup SplashActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}