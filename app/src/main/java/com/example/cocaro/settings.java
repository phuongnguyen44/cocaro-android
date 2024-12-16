package com.example.cocaro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class settings extends AppCompatActivity {
    TextView ok;
    Switch music;
    MediaPlayer media;
    private static final  String PREFS_NAME = "QuanLyBatTatSwitch";
    private static final  String check="check";

    boolean checksw=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        music=(Switch) findViewById(R.id.swmusic);
        ok=(TextView) findViewById(R.id.btnok);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedValue = settings.getString(check, "");
        if(savedValue.length() !=0){
            if(savedValue.equals("false")){
                music.setChecked(false);
                checksw=false;
            }
            else {
                music.setChecked(true);
                checksw=true;
            }
        }


        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                        Intent intent=new Intent(settings.this,AudioService.class);
                        startService(intent);
                        checksw=true;





                }
                else{
                    Intent intent=new Intent(settings.this,AudioService.class);
                    stopService(intent);

                    checksw=false;


                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(settings.this,MainActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(check,  checksw+"");





        // Áp dụng thay đổi
        editor.apply();
    }
}