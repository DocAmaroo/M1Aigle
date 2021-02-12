package com.example.availablesensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SensorAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<MySensor> sensors;
    private final LayoutInflater inflater;

    public SensorAdapter(Context context, ArrayList<MySensor> sensors) {
        this.context = context;
        this.sensors = sensors;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return sensors.size();
    }

    @Override
    public Object getItem(int position) {
        return sensors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.sensor_item, null);

        MySensor currentSensor = (MySensor) getItem(position);

        TextView itemNameView = convertView.findViewById(R.id.sensor_name);
        itemNameView.setText(currentSensor.getType() + ": " + currentSensor.getName());
        if (currentSensor.getState()) {
            itemNameView.setBackgroundColor(convertView.getResources().getColor(R.color.lightgreen));
        } else {
            itemNameView.setBackgroundColor(convertView.getResources().getColor(R.color.lightred));
        }

        return convertView;
    }
}
