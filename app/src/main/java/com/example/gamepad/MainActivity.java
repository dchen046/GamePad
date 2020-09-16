package com.example.gamepad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Process game selection of tic-tac-toe
     * @param view screen
     */
    public void tttClick(View view) {
        Intent intent = new Intent(this, TicActivityHome.class);
        startActivity(intent);
    }

    /**
     * Process game selection of Hangman
     * @param view screen
     */
    public void hangmanClick(View view) {
        Intent intent = new Intent(this, HangActivity.class);
        startActivity(intent);
    }

}