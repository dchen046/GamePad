package com.example.gamepad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class TicActivityHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Process the click of the Player VS Player button
     * @param view screen
     */
    public void pvpClick(View view) {
        Intent intent = new Intent(this, TicActivityPvp.class);
        startActivity(intent);
    }

    /**
     * Process the click of the Player VS Computer button
     * @param view screen
     */
    public void pvaClick(View view) {
        Intent intent = new Intent(this, TicActivityPvc.class);
        startActivity(intent);
    }

}