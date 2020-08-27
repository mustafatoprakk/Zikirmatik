package com.example.zikirmatik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int plus, increase;
    Button btnSayac, btn_recycle, btn_recycle2;
    SharedPreferences preferences;
    SharedPreferences preferencesIncrease;
    SharedPreferences  preferencesDelete;
    public Vibrator vibrator;
    TextView increasetextView;
    ConstraintLayout backgroundColor;
    Toolbar setting;
    TextView saveTextView, deleteText, zikirSilText, tesbihatSilText, lineText;
    int saveboolean;
    Switch switchColor;
    boolean doubleBackToExitPressedOnce=false;
    Chronometer chronometer;
    private boolean isResume;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min;


    @Override
    protected void onPause() {

        //Save
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("count_key",plus);
        editor.commit();

        SharedPreferences.Editor editor2=preferencesIncrease.edit();
        editor2.putInt("increase_key",increase);
        editor2.commit();

        //SharedPreferences.Editor save=preferencesSaveText.edit();
        //save.putInt("save_key",result);
        //save.commit();

        SharedPreferences.Editor delete=preferencesDelete.edit();
        delete.putInt("delete_key",saveboolean);
        delete.commit();

        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fullscreen activity
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        btnSayac=findViewById(R.id.btnSayac);
        vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        increasetextView=findViewById(R.id.increasetextView);
        backgroundColor=findViewById(R.id.backgroundColor);
        saveTextView=findViewById(R.id.saveTextView);
        deleteText=findViewById(R.id.deleteText);
        setting=findViewById(R.id.settings);
        switchColor=findViewById(R.id.switchColor);
        btn_recycle=findViewById(R.id.btn_recycle);
        btn_recycle2=findViewById(R.id.btn_recycle2);
        zikirSilText=findViewById(R.id.zikirSİlText);
        tesbihatSilText=findViewById(R.id.tesbihatSilText);
        lineText=findViewById(R.id.lineText);
        chronometer= findViewById(R.id.ch);
        handler = new Handler();



        btnSayac.setVisibility(View.VISIBLE);
        increasetextView.setVisibility(View.VISIBLE);
        switchColor.setVisibility(View.INVISIBLE);
        btn_recycle.setVisibility(View.INVISIBLE);
        btn_recycle2.setVisibility(View.INVISIBLE);
        zikirSilText.setVisibility(View.INVISIBLE);
        tesbihatSilText.setVisibility(View.INVISIBLE);
        lineText.setVisibility(View.INVISIBLE);



        //Save
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferencesIncrease=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferencesDelete=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        plus=preferences.getInt("count_key",0);
        btnSayac.setText(""+plus);

        increase=preferencesIncrease.getInt("increase_key",0);
        increasetextView.setText(""+increase);

        saveboolean=preferencesDelete.getInt("delete_key",0);
        deleteText.setText(""+saveboolean);


        Toast toast=Toast.makeText(getApplicationContext(),"بِسْــــــــــــــــــــــمِ اﷲِارَّحْمَنِ ارَّحِيم",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,150);
        toast.show();

        // Color save
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);
        switchColor.setChecked(sharedPreferences.getBoolean("value",true));

        if (!isResume){
            tStart = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            chronometer.start();
            isResume = true;

        }else {
            tBuff += tMilliSec;
            handler.removeCallbacks(runnable);
            chronometer.stop();
            isResume = false;
        }

        if (switchColor.isChecked()){

            backgroundColor.setBackgroundColor(Color.WHITE);
            switchColor.setTextColor(Color.BLACK);
            increasetextView.setTextColor(Color.BLACK);
            chronometer.setTextColor(Color.BLACK);
            tesbihatSilText.setTextColor(Color.BLACK);
            zikirSilText.setTextColor(Color.BLACK);
            lineText.setTextColor(Color.BLACK);
            switchColor.setText("Açık Tema");
        }else {
            backgroundColor.setBackgroundColor(Color.BLACK);
            increasetextView.setTextColor(Color.WHITE);
            switchColor.setTextColor(Color.WHITE);
            chronometer.setTextColor(Color.WHITE);
            tesbihatSilText.setTextColor(Color.WHITE);
            zikirSilText.setTextColor(Color.WHITE);
            lineText.setTextColor(Color.WHITE);
            switchColor.setText("Koyu Tema");
        }

        switchColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchColor.isChecked()){

                    SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchColor.setChecked(true);
                    backgroundColor.setBackgroundColor(Color.WHITE);
                    switchColor.setTextColor(Color.BLACK);
                    increasetextView.setTextColor(Color.BLACK);
                    chronometer.setTextColor(Color.BLACK);
                    tesbihatSilText.setTextColor(Color.BLACK);
                    zikirSilText.setTextColor(Color.BLACK);
                    lineText.setTextColor(Color.BLACK);
                    switchColor.setText("Açık Tema");

                }else{
                    // When switch unchecked
                    SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchColor.setChecked(false);
                    backgroundColor.setBackgroundColor(Color.BLACK);
                    switchColor.setTextColor(Color.WHITE);
                    chronometer.setTextColor(Color.WHITE);
                    increasetextView.setTextColor(Color.WHITE);
                    tesbihatSilText.setTextColor(Color.WHITE);
                    zikirSilText.setTextColor(Color.WHITE);
                    lineText.setTextColor(Color.WHITE);
                    switchColor.setText("Koyu Tema");
                }


            }
        });
        btnSayac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plus++;
                btnSayac.setText(""+plus);
                if (plus==33){
                    vibrator.vibrate(250);
                }
                if (plus==66){
                    vibrator.vibrate(250);
                }
                if (plus==99){
                    vibrator.vibrate(350);
                    plus=0;
                    btnSayac.setText(""+plus);
                    increase++;
                    increasetextView.setText(""+increase);
                }
            }
        });
        backgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plus++;
                btnSayac.setText(""+plus);
                if (plus==33){
                    vibrator.vibrate(250);
                }
                if (plus==66){
                    vibrator.vibrate(250);
                }
                if (plus==99){
                    vibrator.vibrate(350);
                    plus=0;
                    btnSayac.setText(""+plus);

                    increase++;
                    increasetextView.setText(""+increase);
                }
            }
        });
        btn_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus=0;
                btnSayac.setText(""+plus);

                Toast.makeText(MainActivity.this,"Silindi",Toast.LENGTH_LONG).show();
            }
        });
        btn_recycle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase=0;
                increasetextView.setText(""+increase);

                Toast.makeText(MainActivity.this,"Silindi",Toast.LENGTH_LONG).show();
            }
        });

    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min =  sec / 60;
            sec = sec % 60;
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec));
            handler.postDelayed(this, 60);
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                btnSayac.setVisibility(View.INVISIBLE);
                increasetextView.setVisibility(View.INVISIBLE);
                chronometer.setVisibility(View.INVISIBLE);
                switchColor.setVisibility(View.VISIBLE);
                btn_recycle.setVisibility(View.VISIBLE);
                btn_recycle2.setVisibility(View.VISIBLE);
                zikirSilText.setVisibility(View.VISIBLE);
                tesbihatSilText.setVisibility(View.VISIBLE);
                lineText.setVisibility(View.VISIBLE);
                backgroundColor.setEnabled(false);

                if (!isResume){
                    tMilliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    chronometer.setText("00:00");
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }
    @Override
    public void onBackPressed() {   // geri tuşuna iki kez tıkladığında activity den çıkar

        btnSayac.setVisibility(View.VISIBLE);
        increasetextView.setVisibility(View.VISIBLE);
        chronometer.setVisibility(View.VISIBLE);
        switchColor.setVisibility(View.INVISIBLE);
        btn_recycle.setVisibility(View.INVISIBLE);
        btn_recycle2.setVisibility(View.INVISIBLE);
        zikirSilText.setVisibility(View.INVISIBLE);
        tesbihatSilText.setVisibility(View.INVISIBLE);
        lineText.setVisibility(View.INVISIBLE);
        backgroundColor.setEnabled(true);


        this.doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}


