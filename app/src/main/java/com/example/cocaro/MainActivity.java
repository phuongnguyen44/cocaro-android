package com.example.cocaro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private AudioManager audioManager;

    ImageButton img1, img2 ;
    ImageView img3,img4;
    ConstraintLayout mainLayout;
    AnimationDrawable an1;
    Animation Up,Down;
    Switch sw;
    MediaPlayer ss;

    private void showRulesDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_rules, null);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();

        // Thêm hiệu ứng vào dialog
        dialog.setOnShowListener(dialogInterface -> {
            View decorView = dialog.getWindow().getDecorView();
            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            fadeIn.setDuration(500); // thời gian hiệu ứng (500ms)
            decorView.startAnimation(fadeIn);
        });

        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.backgr), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        mainLayout = findViewById(R.id.backgr);
        an1 = (AnimationDrawable) mainLayout.getBackground();
        an1.setEnterFadeDuration(1000);
        an1.setExitFadeDuration(2000);
        an1.start();

        Up = AnimationUtils.loadAnimation(this,R.anim.up);
        Down = AnimationUtils.loadAnimation(this,R.anim.down);
        final MediaPlayer s1 = MediaPlayer.create(this,R.raw.click);
        img1 = findViewById(R.id.imgbtn1);
        img2 = findViewById(R.id.imgbtn2);
        img3 = findViewById(R.id.imgbtn3);
        img4 = findViewById(R.id.imgbtn4);



        img1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    img1.startAnimation(Up);
                    s1.start();
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    img1.startAnimation(Down);
                    Intent it = new Intent(MainActivity.this, act_chon_che_do.class);
                    startActivity(it);
                }
                return true;
            }
        });
        img2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    s1.start();
                    img2.startAnimation(Up);
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    img2.startAnimation(Down);
                    finishAffinity();
                    System.exit(0);
                }
                return true;
            }
        });
        img3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    s1.start();
                    img3.startAnimation(Up);
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    img3.startAnimation(Down);
                    showRulesDialog(MainActivity.this);
                }
                return true;
            }
        });
        img4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    s1.start();
                    img4.startAnimation(Up);
                } else if (event.getAction()==MotionEvent.ACTION_UP) {
                    img4.startAnimation(Down);
                   Intent intent=new Intent(MainActivity.this,settings.class);
                   startActivity(intent);
                }
                return true;
            }
        });

    }
    private void saveMediaState(boolean isMuted) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("media_muted", isMuted);
        editor.apply();
    }

    private boolean isMediaMuted() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean("media_muted", false);
    }

    protected void muteMedia() {
        if (audioManager != null) {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            saveMediaState(true);
        }
    }

    protected void unmuteMedia() {
        if (audioManager != null) {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            saveMediaState(false);
        }
    }

}