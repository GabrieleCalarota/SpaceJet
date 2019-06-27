package com.onthewifi.casacalarota.spacejet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        private ImageButton buttonPlay;
        private int scoreRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);

        SharedPreferences prefs = this.getSharedPreferences("score", Context.MODE_PRIVATE);
        scoreRecord = prefs.getInt("highScore",0);
        buttonPlay.setOnClickListener(this);

    }

    public void alert(String message, String title){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(message);
        myAlert.setPositiveButton(getResources().getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                play();
            }
        });
        myAlert.setNeutralButton(getResources().getString(R.string.back_menu),new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        myAlert.setNegativeButton(getResources().getString(R.string.quit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        myAlert.setTitle(title);
        myAlert.setIcon(R.mipmap.ic_launcher);
        myAlert.create();
        myAlert.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle b = data.getExtras();
                int score  = b.getInt("score");
                if (score < scoreRecord)
                    alert(getResources().getString(R.string.loose_message)+score,getResources().getString(R.string.loose_title));
                else {
                    scoreRecord = score;
                    SharedPreferences prefs = this.getSharedPreferences("score", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("highScore", scoreRecord);
                    editor.commit();
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.victory);
                    mp.start();
                    alert(getResources().getString(R.string.record_message)+score,getResources().getString(R.string.record_title));
                }
            }
        }
    }


    public void play(){
        startActivityForResult(new Intent(this,GameActivity.class),1);
    }

    @Override
    public void onClick(View v){
        play();
    }

    public void getHigherScore(View view){
        alert(getResources().getString(R.string.show_record_message)+scoreRecord,getResources().getString(R.string.show_record_title));
    }
}
