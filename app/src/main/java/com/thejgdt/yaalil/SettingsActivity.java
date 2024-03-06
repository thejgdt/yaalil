package com.thejgdt.yaalil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LinearLayout backBar = findViewById(R.id.backBar);
        backBar.setOnClickListener(v -> onBackPressed());

        RelativeLayout bugLayout = findViewById(R.id.bugLayout);
        bugLayout.setOnClickListener(v -> captureLogCatAndSendEmail());
    }

    private void captureLogCatAndSendEmail() {
        // Check permission to write external storage
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            return;
        }

        // Get logcat output
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            TimeUnit.SECONDS.sleep(1); // Wait for logcat to finish
            String logcatOutput = getLogcatOutput(process);
            writeLogcatToFile(logcatOutput);
        } catch (Exception e) {
            Log.e(TAG, "Error capturing logcat: " + e.getMessage());
            Toast.makeText(this, "Error capturing logcat", Toast.LENGTH_SHORT).show();
        }
    }

    private String getLogcatOutput(Process process) throws IOException {
        StringBuilder logcatOutput = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            logcatOutput.append(line).append("\n");
        }

        bufferedReader.close();
        return logcatOutput.toString();
    }

    private void writeLogcatToFile(String logcatOutput) {
        try {
            File logFile = new File(Environment.getExternalStorageDirectory(), "logcat.txt");
            FileOutputStream fos = new FileOutputStream(logFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.write(logcatOutput);
            outputStreamWriter.close();
            fos.close();
            sendEmail(logFile);
        } catch (IOException e) {
            Log.e(TAG, "Error writing logcat to file: " + e.getMessage());
            Toast.makeText(this, "Error writing logcat to file", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(File attachment) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"theaberchio@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please see attached logcat file.");
        Uri uri = Uri.fromFile(attachment);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}