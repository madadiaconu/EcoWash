package com.myapps.ecowash.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.model.Reservation;
import com.myapps.ecowash.util.DialogManager;
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
            viewHolder.cancel = (ImageView) convertView.findViewById(R.id.cancelReservation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Reservation currentReservation = reservations.get(position);
        if(currentReservation != null) {
            viewHolder.date.setText(currentReservation.getDate());
            viewHolder.hour.setText(Html.fromHtml(currentReservation.getHour()+"<sup>00</sup>"));
            viewHolder.machine.setText(currentReservation.getWashingMachine().getName());
            viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.createDialog(
                            getContext(),
                            R.string.confirm_cancel_reservation,
                            R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        ParseClient.getInstance().cancelReservation(currentReservation.getObjectId());
                                        reservations.remove(currentReservation);
                                        notifyDataSetChanged();
                                    } catch (ParseException e) {
                                        DialogManager.createSimpleDialog(getContext(),R.string.myreservation_cancer_error).show();
                                        e.printStackTrace();
                                    }
                                }
                            },
                            R.string.no,
                            null).show();
                }
            });
        }
        return convertView;
    }

    static class ViewHolder{
        TextView date;
        TextView hour;
        TextView machine;
        ImageView cancel;
    }
}
