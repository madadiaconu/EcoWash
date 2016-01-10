package com.myapps.ecowash.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myapps.ecowash.R;
import com.myapps.ecowash.model.AvailabilityByHour;

import java.util.List;

public class AvailabilityAdapter extends ArrayAdapter<AvailabilityByHour>{

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
        if (convertView == null){
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.hour = (TextView) convertView.findViewById(R.id.availabilityHour);
            viewHolder.nbOfMachines = (TextView) convertView.findViewById(R.id.availabilityNumber);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final AvailabilityByHour currentItem = availabilityByHourList.get(position);
        if(currentItem != null) {
            viewHolder.hour.setText(Html.fromHtml(currentItem.getHour() + "<sup>00</sup>"));
            viewHolder.nbOfMachines.setText(currentItem.getWashingMachines().size()+"");
        }
        return convertView;
    }

    static class ViewHolder{
        TextView hour;
        TextView nbOfMachines;
    }
}
