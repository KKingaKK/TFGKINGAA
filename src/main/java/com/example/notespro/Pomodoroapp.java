package com.example.notespro;
import static com.example.notespro.R.id.tvTimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pomodoroapp extends AppCompatActivity {
    private TextView tvTimer;
    private Button btnStart, btnReset, volver;

    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis = 1500000; // 25 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoroapp);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
       volver =findViewById(R.id.volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pomodoroapp.this, MainActivity.class));
            }
        });

        updateTimer();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                btnStart.setText("Start");
                playAlarm();
                Toast.makeText(Pomodoroapp.this, "Pomodoro completed!", Toast.LENGTH_SHORT).show();
            }
        }.start();

        isRunning = true;
        btnStart.setText("Pause");
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isRunning = false;
        btnStart.setText("Start");
    }

    private void resetTimer() {
        countDownTimer.cancel();
        isRunning = false;
        timeLeftInMillis = 1500000;
        updateTimer();
        btnStart.setText("Start");
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        tvTimer.setText(timeFormatted);
    }

    private void playAlarm() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        mediaPlayer.start();

    }
}
