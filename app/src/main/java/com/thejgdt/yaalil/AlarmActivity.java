package com.thejgdt.yaalil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    private static final String SWITCH_STATE = "switch_state";
    private Switch optionSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        optionSwitch = findViewById(R.id.option_switch);
        optionSwitch.setChecked(sharedPreferences.getBoolean("switch_state", false));

        // Panggil metode setReminder untuk mengatur notifikasi
        optionSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                NotificationScheduler.setReminder(AlarmActivity.this, AlarmReceiver.class);
            } else {
                NotificationScheduler.cancelReminder(AlarmActivity.this, AlarmReceiver.class);
            }
        });

        LinearLayout backBar = findViewById(R.id.backBar);
        backBar.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SWITCH_STATE, optionSwitch.isChecked());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("switch_state", optionSwitch.isChecked());
        editor.apply();

        // Panggil metode cancelReminder untuk membatalkan notifikasi
        NotificationScheduler.cancelReminder(this, AlarmReceiver.class);
    }
}

class NotificationScheduler {

    public static void setReminder(Context context, Class<? extends BroadcastReceiver> cls) {
        Calendar setcalendar = getCalendar();

        // Intent yang akan dipanggil ketika notifikasi diklik
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Atur alarm
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }

    @NonNull
    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();

        // Set waktu notifikasi
        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        setcalendar.set(Calendar.HOUR_OF_DAY, 19);
        setcalendar.set(Calendar.MINUTE, 0);
        setcalendar.set(Calendar.SECOND, 0);

        // Cek jika waktu sekarang sudah lewat dari jadwal, jika ya, tambahkan satu minggu
        if (calendar.after(setcalendar)) {
            setcalendar.add(Calendar.DATE, 7);
        }
        return setcalendar;
    }

    public static void cancelReminder(Context context, Class<?> cls) {
        // Batalkan alarm
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}