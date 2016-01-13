package com.myapps.ecowash.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.myapps.ecowash.R;
import com.myapps.ecowash.ui.adapters.NewReservationPagerAdapter;

public class MakeReservationActivity extends BaseActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);
        viewPager = (ViewPager)findViewById(R.id.newReservationPager);
        NewReservationPagerAdapter myPagerAdapter = new NewReservationPagerAdapter(MakeReservationActivity.this,getLayoutInflater());
        viewPager.setAdapter(myPagerAdapter);
    }
}
