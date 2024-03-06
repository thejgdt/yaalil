package com.thejgdt.yaalil;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TahlilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahlil);

        LinearLayout backBar = findViewById(R.id.backBar);
        backBar.setOnClickListener(v -> onBackPressed());

        ScrollView yaasinView = findViewById(R.id.mainView);

        try {
            // Baca file JSON dari resources
            InputStream inputStream = getResources().openRawResource(R.raw.yaasin);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Buat objek JSONObject dari string JSON
            JSONObject jsonObject = new JSONObject(json);

            // Ambil teks Surah Yasin dari objek JSON
            JSONArray ayahs = jsonObject.getJSONObject("data").getJSONArray("ayahs");
            StringBuilder surahYasinText = new StringBuilder();
            for (int i = 0; i < ayahs.length(); i++) {
                surahYasinText.append(ayahs.getJSONObject(i).getString("text")).append("\n");
            }

            // Tampilkan teks Surah Yasin di ScrollView
            TextView textViewSurahYasin = new TextView(this);
            textViewSurahYasin.setText(surahYasinText.toString());
            yaasinView.addView(textViewSurahYasin); // Tambahkan TextView ke ScrollView
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}