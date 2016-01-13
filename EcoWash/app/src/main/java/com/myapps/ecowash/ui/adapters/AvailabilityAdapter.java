package com.myapps.ecowash.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.bl.ParseCallback;
import com.myapps.ecowash.bl.ParseClient;
import com.myapps.ecowash.model.AvailabilityByHour;
import com.myapps.ecowash.util.DialogManager;
import com.parse.ParseException;

import java.util.List;

public class AvailabilityAdapter extends ArrayAdapter<AvailabilityByHour> {

    private int layout;
    private List<AvailabilityByHour> availabilityByHourList;

    public AvailabilityAdapter(Context context, int resource, List<AvailabilityByHour> objects) {
        super(context, resource, objects);
        this.layout = resource;
        this.availabilityByHourList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.hour = (TextView) convertView.findViewById(R.id.availabilityHour);
            viewHolder.nbOfMachines = (TextView) convertView.findViewById(R.id.availabilityNumber);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AvailabilityByHour currentItem = availabilityByHourList.get(position);
        if (currentItem != null) {
            viewHolder.hour.setText(Html.fromHtml(currentItem.getHour() + "<sup>00</sup>"));
            viewHolder.nbOfMachines.setText(currentItem.getWashingMachines().size() + "");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = getContext().getResources().getString(R.string.make_reservation_confirmation) + " " + currentItem.getDate() + ", " + currentItem.getHour() + "h00?";
                DialogManager.createDialog(
                        getContext(),
                        message,
                        R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ParseClient.getInstance().makeReservation(currentItem.getDate(), currentItem.getHour(), new ParseCallback<String>() {
                                    @Override
                                    public void onSuccess(String machineName) {
                                        String message = getContext().getResources().getString(R.string.reservation_confirmed) + machineName;
                                        DialogManager.createSimpleDialog(getContext(), message).show();
                                    }

                                    @Override
                                    public void onFailure(ParseException exception) {
                                        DialogManager.createSimpleDialog(getContext(), R.string.reservation_failed).show();
                                    }
                                });

                            }
                        }, R.string.no,
                        null).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView hour;
        TextView nbOfMachines;
    }
}
