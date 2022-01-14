package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    int total_seconds = 0;
    boolean is_running;
    boolean was_running;

    TextView textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            total_seconds = savedInstanceState.getInt("total_seconds");
            is_running = savedInstanceState.getBoolean("is_running");
            was_running = savedInstanceState.getBoolean("was_running");
        }

        textViewTimer = findViewById(R.id.textViewTimer);
        timerWork();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("total_seconds", total_seconds);
        outState.putBoolean("is_running", is_running);
        outState.putBoolean("was_running", was_running);
    }

    @Override
    protected void onPause() {
        super.onPause();
        was_running = is_running;
        is_running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        is_running = was_running;
    }

    public void timerStart(View view) {
        is_running = true;
    }

    public void timerPause(View view) {
        is_running = false;
    }

    public void timerReset(View view) {
        is_running = false;
        total_seconds = 0;
        textViewTimer.setText(R.string.textViewTimer_default);
    }

    public void timerWork() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = total_seconds / 3600;
                int minutes = (total_seconds - (hours * 3600)) / 60;
                int seconds = total_seconds - ((hours * 3600) + (minutes * 60));
                textViewTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
                if (is_running) {
                    total_seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}