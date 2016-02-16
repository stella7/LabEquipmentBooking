package com.example.android.equipmentbookingtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyameng on 15/12/2.
 */
public class DeviceAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public DeviceAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Devices object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        DevicesHolder devicesHolder;

        if (row == null)
        {
            LayoutInflater layoutInflater =(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            devicesHolder = new DevicesHolder();
            devicesHolder.tx_name = (TextView)row.findViewById(R.id.tx_name);
           // devicesHolder.tx_details = (TextView)row.findViewById(R.id.tx_details);
            row.setTag(devicesHolder);
        }
        else
        {
            devicesHolder = (DevicesHolder)row.getTag();
        }

        Devices devices = (Devices)this.getItem(position);
        devicesHolder.tx_name.setText(devices.getName());
       // devicesHolder.tx_details.setText(devices.getDetails());

        return row;
    }

    static class DevicesHolder
    {
        TextView tx_name;
        //TextView tx_details;
    }
}
