package com.myapps.ecowash.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.model.Reservation;
import com.parse.ParseException;

import java.util.List;

public class MyReservationsAdapter extends ArrayAdapter<Reservation>{

    private int layout;
    private List<Reservation> reservations;

    public MyReservationsAdapter(Context context, int resource, List<Reservation> objects) {
        super(context, resource, objects);
        this.layout = resource;
        this.reservations = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.reservationDate);
            viewHolder.hour = (TextView) convertView.findViewById(R.id.reservationHour);
            viewHolder.machine = (TextView) convertView.findViewById(R.id.reservationMachine);
            viewHolder.cancelBtn = (Button) convertView.findViewById(R.id.cancelReservation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Reservation currentReservation = reservations.get(position);
        if(currentReservation != null) {
            Log.d("zzz",currentReservation.toString());
            viewHolder.date.setText(currentReservation.getDate());
            viewHolder.hour.setText(currentReservation.getHour()+"");
            viewHolder.machine.setText(currentReservation.getWashingMachine().getName());
            viewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ParseClient.getInstance().cancelReservation(currentReservation.getObjectId());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setMessage("Could not cancel reservation. Please try again.");
                        alertDialogBuilder.setPositiveButton("Ok", null);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            });
        }
        return convertView;
    }

    static class ViewHolder{
        TextView date;
        TextView hour;
        TextView machine;
        Button cancelBtn;
    }
}
