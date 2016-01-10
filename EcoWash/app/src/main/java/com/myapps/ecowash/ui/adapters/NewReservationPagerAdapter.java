package com.myapps.ecowash.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseCallback;
import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.model.AvailabilityByHour;
import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.util.DateHandler;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewReservationPagerAdapter extends android.support.v4.view.PagerAdapter {

    private List<Date> availableDays;
    private Context context;
    private LayoutInflater layoutInflater;

    public NewReservationPagerAdapter (Context context, LayoutInflater layoutInflater){
        this.availableDays = new ArrayList<>();
        this.context = context;
        this.layoutInflater = layoutInflater;

        Date myDate = DateHandler.currentDate();
        availableDays.add(myDate);
        for (int i=0;i<9;i++){
            myDate = DateHandler.getNextDate(myDate);
            availableDays.add(myDate);
        }
    }

    @Override
    public int getCount() {
        return availableDays.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final String currentDateToString = DateHandler.dateToString(availableDays.get(position));

        final View pagerView = layoutInflater.inflate(R.layout.view_availability_by_date, null);
        TextView newReservationDate = (TextView) pagerView.findViewById(R.id.dateTextView);
        newReservationDate.setText(currentDateToString);

        final ListView availabilitySchedule = (ListView) pagerView.findViewById(R.id.availabilityByHour);
        ParseClient.getInstance().getReservations(currentDateToString, new ParseCallback<List<Reservation>>() {
            @Override
            public void onSuccess(List<Reservation> result) {
                List<AvailabilityByHour> objects = new ArrayList<>();
                for (int i = 8; i <= 20; i++) {
                    objects.add(new AvailabilityByHour(currentDateToString, i));
                }
                for (Reservation r : result) {
                    objects.get(r.getHour() - 8).addWashingMachine(r.getWashingMachine());
                }
                AvailabilityAdapter adapter = new AvailabilityAdapter(context, R.layout.view_item_availability, objects);
                availabilitySchedule.setAdapter(adapter);
                container.addView(pagerView);
            }

            @Override
            public void onFailure(ParseException exception) {

            }
        });

        return pagerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
