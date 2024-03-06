package com.thejgdt.yaalil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private boolean exitConfirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menghubungkan logo Yaalil dengan memunculkan toast
        LinearLayout yaalilLayout = findViewById(R.id.logoTitleLayout);
        yaalilLayout.setOnClickListener(v -> Toast.makeText(this, "Yaalil", Toast.LENGTH_SHORT).show());

        // Menghubungkan logo Surah Yaasin dengan membuka layout baru
        LinearLayout yaasinLayout = findViewById(R.id.box1Layout);
        yaasinLayout.setOnClickListener(v -> {
            Intent bukaYaasin = new Intent(MainActivity.this, YaasinActivity.class);
            startActivity(bukaYaasin);
        });

        // Menghubungkan logo Tahlil dengan membuka layout baru
        LinearLayout tahlilLayout = findViewById(R.id.box2Layout);
        tahlilLayout.setOnClickListener(v -> {
            Intent bukaTahlil = new Intent(MainActivity.this, TahlilActivity.class);
            startActivity(bukaTahlil);
        });

        // Menghubungkan logo Surah Pendek dengan membuka layout baru
        LinearLayout surahPendekLayout = findViewById(R.id.box3Layout);
        surahPendekLayout.setOnClickListener(v -> {
            Intent bukaSurahPendek = new Intent(MainActivity.this, SurahPendekActivity.class);
            startActivity(bukaSurahPendek);
        });

        // Menghubungkan tombol wiki dengan fungsi showPopup
        Button wikiButton = findViewById(R.id.wikiButton);
        wikiButton.setOnClickListener(v -> showPopup());

        // Menghubungkan tombol info dengan fungsi bottomsheet
        ImageButton infoButton = findViewById(R.id.infoIcon);
        infoButton.setOnClickListener(v -> showDialog());

        // Menghubungkan tombol alarm dengan membuka layout baru
        ImageButton alarmButton = findViewById(R.id.alarmsIcon);
        alarmButton.setOnClickListener(v -> {
            Intent bukaAlarm = new Intent(MainActivity.this, AlarmActivity.class);
            startActivity(bukaAlarm);
        });

        // Menghubungkan tombol settings dengan membuka layout baru
        ImageButton settingsButton = findViewById(R.id.settingsIcon);
        settingsButton.setOnClickListener(v -> {
            Intent bukaSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(bukaSettings);
        });
    }

    @Override
    public void onBackPressed() {
        if (exitConfirmed) {
            super.onBackPressed();
        } else {
            showExitConfirmationDialog();
        }
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this).setMessage("Apakah Anda yakin ingin menutup aplikasi?").setCancelable(false).setPositiveButton("Ya", (dialog, id) -> {
            exitConfirmed = true;
            onBackPressed();
        }).setNegativeButton("Tidak", (dialog, id) -> {
        }).show();
    }

    // Menampilkan pop-up dengan dua tombol
    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.activity_popup, null);
        builder.setView(popupView);

        AlertDialog dialog = builder.create();

        // Menambahkan animasi saat pop-up muncul
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        Button yaasinButton = popupView.findViewById(R.id.yaasinButton);
        yaasinButton.setOnClickListener(v -> {
            // Ketika tombol YAASIN diklik
            // Ganti URL dengan URL yang diinginkan
            String url = "https://ms.wikipedia.org/wiki/Surah_Yaasin";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            dialog.dismiss();
        });

        Button tahlilButton = popupView.findViewById(R.id.tahlilButton);
        tahlilButton.setOnClickListener(v -> {
            // Ketika tombol TAHLIL diklik
            // Ganti URL dengan URL yang diinginkan
            String url = "https://id.wikipedia.org/wiki/Tahlil";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            dialog.dismiss();
        });

        TextView txtClose = popupView.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(v -> dialog.dismiss()); // Ketika tombol "x" diklik

        dialog.show();
    }

    // Menampilkan bottom sheet saat tombol info diklik
    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_bottomsheet);

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        // Menghubungkan ImageButton githubIcon dari dialog
        ImageButton githubIcon = dialog.findViewById(R.id.githubIcon);
        githubIcon.setOnClickListener(v -> {
            String url = "https://github.com/thejgdt";
            Intent bukaGithub = new Intent(Intent.ACTION_VIEW);
            bukaGithub.setData(Uri.parse(url));
            startActivity(bukaGithub);
            dialog.dismiss();
        });

        // Menghubungkan ImageButton instagramIcon dari dialog
        ImageButton instagramIcon = dialog.findViewById(R.id.instagramIcon);
        instagramIcon.setOnClickListener(v -> {
            String url = "https://instagram.com/fyajgdt";
            Intent bukaInstagram = new Intent(Intent.ACTION_VIEW);
            bukaInstagram.setData(Uri.parse(url));
            startActivity(bukaInstagram);
            dialog.dismiss();
        });

        // Menghubungkan TextView contactButton dari dialog
        TextView contactButton = dialog.findViewById(R.id.contactButton);
        contactButton.setOnClickListener(v -> {
            String email = "theaberchio@gmail.com";
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            startActivity(emailIntent);
            dialog.dismiss();
        });
    }
}