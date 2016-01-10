package com.myapps.ecowash.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.myapps.ecowash.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigate to MyReservations screen
        findViewById(R.id.myReservations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(MyReservationsActivity.class);
            }
        });

        //Navigate to MakeReservation screen
        findViewById(R.id.newReservation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(MakeReservationActivity.class);
            }
        });

        //Navigate to WashNow screen
        findViewById(R.id.washNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(WashNowActivity.class);
            }
        });
    }
}
