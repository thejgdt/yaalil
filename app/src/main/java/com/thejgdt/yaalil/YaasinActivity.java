package com.thejgdt.yaalil;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class YaasinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaasin);

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

            // Ambil array "ayahs" dari objek JSON
            JSONArray ayahsArray = jsonObject.getJSONArray("ayahs");

            // Buat LinearLayout sebagai container untuk teks Arab dan terjemahan
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            // Iterasi melalui setiap objek dalam array "ayahs"
            for (int i = 0; i < ayahsArray.length(); i++) {
                JSONObject ayahObject = ayahsArray.getJSONObject(i);

                // Ambil teks Arab dari objek "ayahs"
                String arabicText = ayahObject.getString("arab");

                // Ambil terjemahan dari objek "ayahs"
                String translation = ayahObject.getString("translation");

                // Konversi HTML entities ke karakter Unicode
                arabicText = convertHtmlEntities(arabicText);
                translation = convertHtmlEntities(translation);

                // Buat TextView untuk teks Arab
                TextView arabicTextView = new TextView(this);
                arabicTextView.setText(arabicText);
                arabicTextView.setTextSize(24); // Atur ukuran font sesuai keinginan
                arabicTextView.setTextColor(Color.BLACK); // Warna teks Arab
                arabicTextView.setPadding(0, 20, 0, 10); // Atur padding atas dan bawah

                // Buat TextView untuk terjemahan
                TextView translationTextView = new TextView(this);
                translationTextView.setText(translation);
                translationTextView.setTextSize(14); // Atur ukuran font sesuai keinginan
                translationTextView.setTextColor(Color.BLACK); // Warna terjemahan
                translationTextView.setPadding(0, 10, 0, 20); // Atur padding atas dan bawah

                // Atur warna background sesuai dengan indeks ayat (ganjil/genap)
                if (i % 2 == 0) {
                    arabicTextView.setBackgroundColor(Color.parseColor("#DADADA")); // Warna untuk ayat genap
                    translationTextView.setBackgroundColor(Color.parseColor("#DADADA")); // Warna untuk terjemahan genap
                } else {
                    arabicTextView.setBackgroundColor(Color.parseColor("#00000000")); // Warna untuk ayat ganjil
                    translationTextView.setBackgroundColor(Color.parseColor("#00000000")); // Warna untuk terjemahan ganjil
                }

                // Tambahkan TextView teks Arab dan terjemahan ke LinearLayout
                linearLayout.addView(arabicTextView);
                linearLayout.addView(translationTextView);

                // Atur LayoutParams untuk arabicTextView
                LinearLayout.LayoutParams arabicLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                arabicTextView.setLayoutParams(arabicLayoutParams);

                // Atur LayoutParams untuk translationTextView
                LinearLayout.LayoutParams translationLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                translationTextView.setLayoutParams(translationLayoutParams);
            }

            // Tambahkan LinearLayout ke ScrollView
            yaasinView.addView(linearLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fungsi untuk mengonversi HTML entities menjadi karakter Unicode
    private String convertHtmlEntities(String textWithEntities) {
        return Html.fromHtml(textWithEntities, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}