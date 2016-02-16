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
 * Created by liyameng on 15/12/3.
 */
public class BookingAdapter extends ArrayAdapter{
    List list = new ArrayList();

    public BookingAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Bookings object) {
        super.add(object);
        list.add(object);
    }
    public void delete(Bookings object){
        super.remove(object);
        list.remove(object);
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
        View ro;
        ro = convertView;
        BookingHolder bookingHolder;
        if (ro == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ro = layoutInflater.inflate(R.layout.ro_layout,parent,false);
            bookingHolder = new BookingHolder();
            bookingHolder.tx_device = (TextView)ro.findViewById(R.id.tx_device);
            bookingHolder.tx_time = (TextView)ro.findViewById(R.id.tx_time);
            ro.setTag(bookingHolder);
        }
        else
        {
            bookingHolder = (BookingHolder)ro.getTag();
        }

        Bookings bookings = (Bookings)this.getItem(position);
        bookingHolder.tx_device.setText(bookings.getDevice());
        bookingHolder.tx_time.setText(bookings.getTime());

        return ro;
    }

    static class BookingHolder
    {
        TextView tx_device, tx_time;
    }
}
