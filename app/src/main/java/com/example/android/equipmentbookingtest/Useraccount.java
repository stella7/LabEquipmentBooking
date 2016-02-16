package com.example.android.equipmentbookingtest;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Useraccount extends AppCompatActivity {

    String json_booking;
    JSONObject jsonObject;
    JSONArray jsonArray;
    BookingAdapter bookingAdapter;
    ListView listView;
   // List<ListViewItem> items;
    private static final String TAG = "Useraccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccount);

        bookingAdapter = new BookingAdapter(this,R.layout.ro_layout);
        listView = (ListView)findViewById(R.id.list_history);
        listView.setAdapter(bookingAdapter);

        json_booking = getIntent().getExtras().getString("booking");

        registerForContextMenu(listView);

        try {
            jsonObject = new JSONObject(json_booking);
            jsonArray = jsonObject.getJSONArray("booking");
            int count = 0;
            String device,time;

            while(count<jsonArray.length())
            {
                JSONObject JO_BOOK = jsonArray.getJSONObject(count);
                device = JO_BOOK.getString("device");
                time = JO_BOOK.getString("time");
                Bookings bookings = new Bookings(device,time);
                bookingAdapter.add(bookings);

                count++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_account, menu);

       /*
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            Log.e(TAG, "bad menuInfo", e);
            return;
        }
        */
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

       // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            Log.e(TAG, "bad menuInfo", e);
            return false;
        }
       // Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);

        switch(item.getItemId()){
            case R.id.action_delete:

               bookingAdapter.remove(info.position);
                bookingAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
}
