package com.example.android.equipmentbookingtest;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Devicelist extends AppCompatActivity {


    String Json_read_D;
    JSONObject jsonObject;
    JSONArray jsonArray;
    DeviceAdapter deviceAdapter;
    ListView listView;
    String name;
    String details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelist);

        ImageView img= (ImageView) findViewById(R.id.list_icon);
        img.setImageResource(R.drawable.listicon);

        Json_read_D = getIntent().getExtras().getString("json_device_data");


        listView = (ListView)findViewById(R.id.listView);

        deviceAdapter = new DeviceAdapter(this,R.layout.row_layout);
        listView.setAdapter(deviceAdapter);

        try {
            jsonObject = new JSONObject(Json_read_D);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name;
           // String details;

            while(count<jsonArray.length())
            {
                JSONObject JO_DEVICE = jsonArray.getJSONObject(count);
                name = JO_DEVICE.getString("name");

               // details = JO_DEVICE.getString("details");

                Devices devices = new Devices(name);
                deviceAdapter.add(devices);
               // allDevice.add(name);

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        registerClickCallback();
    }



    private void registerClickCallback() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                TextView textView = (TextView) viewClicked.findViewById(R.id.tx_name);

                name = textView.getText().toString();

                new BackgroundTaskOne().execute(name);
/*
                if (details == null)
                {
                    Toast.makeText(Devicelist.this,"Please wait..",Toast.LENGTH_LONG).show();
                }

                    Intent intent = new Intent(Devicelist.this, Showdevice.class);
                    intent.putExtra("deviceName", name);
                  //  intent.putExtra("userEmail", userEmail);
                    intent.putExtra("json_device_one", details);
                    startActivity(intent);
*/
            }
        });
    }



    private void populateListView() {

        listView = (ListView)findViewById(R.id.listView);
        deviceAdapter = new DeviceAdapter(this,R.layout.row_layout);
        listView.setAdapter(deviceAdapter);

        try {
            jsonObject = new JSONObject(Json_read_D);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name;

            while(count<jsonArray.length())
            {
                JSONObject JO_DEVICE = jsonArray.getJSONObject(count);
                name = JO_DEVICE.getString("name");
                // details = JO_DEVICE.getString("details");
                Devices devices = new Devices(name);
                deviceAdapter.add(devices);

                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // Asyntask for search
    class BackgroundTaskOne extends AsyncTask<String,Void,String>
    {
        String json_d_one_url;

        @Override
        protected void onPreExecute() {
            json_d_one_url = "http://devicebooking.site88.net/Json_read_One_D.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            try {
                URL url = new URL(json_d_one_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String temp = "";
                while((temp = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                String finalJson = stringBuilder.toString().trim();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("device_details");

                JSONObject finalObject = parentArray.getJSONObject(0);
                String device_detail = finalObject.getString("details");


                return device_detail;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result ) {
            details = result;

            if (details == null)
            {
                Toast.makeText(Devicelist.this,"Please wait..",Toast.LENGTH_LONG).show();
            }

            else{
                Intent intent = new Intent(Devicelist.this, Showdevice.class);
                intent.putExtra("deviceName", name);
                //  intent.putExtra("userEmail", userEmail);
                intent.putExtra("json_device_one", details);
                startActivity(intent);
            }

            //TextView textView = (TextView)findViewById(R.id.description);
            //textView.setText(result);
        }
    }    //Asyntask for search

}
