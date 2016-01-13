package com.myapps.ecowash.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseCallback;
import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.ui.adapters.MyReservationsAdapter;
import com.parse.ParseException;

import java.util.List;

public class MyReservationsActivity extends BaseActivity {

    private ListView myReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        myReservations = (ListView) findViewById(R.id.myReservationsList);
        addCustomHeader();
        addData();
    }

    private void addCustomHeader(){
        View header = getLayoutInflater().inflate(R.layout.view_head_reservations,null);
        myReservations.addHeaderView(header);
    }

    private void addData(){
        showProgress(R.string.my_reservations_wait);
        ParseClient.getInstance().getMyReservations(new ParseCallback<List<Reservation>>() {
            @Override
            public void onSuccess(List<Reservation> result) {
                MyReservationsAdapter adapter = new MyReservationsAdapter(MyReservationsActivity.this,R.layout.view_item_reservation,result);
                myReservations.setAdapter(adapter);
                hideProgress();
            }

            @Override
            public void onFailure(ParseException exception) {
                exception.printStackTrace();
                hideProgress();
            }
        });
    }
}
