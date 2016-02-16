package com.example.android.equipmentbookingtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class Showdevice extends AppCompatActivity implements View.OnClickListener {

    Button bBook;
    ImageButton btMain;
    TextView tvdeviceName,tvDeviceDetails;
    String js_details;
    String user_email;
    String device_name;
    String details;
    String js_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdevice);

        user_email = getIntent().getExtras().getString("userEmail");
        device_name = getIntent().getExtras().getString("deviceName");

        new BackgroundTaskOne().execute(device_name);

        tvdeviceName = (TextView) findViewById(R.id.deviceName);
        tvdeviceName.setText(getIntent().getExtras().getString("deviceName"));
        tvDeviceDetails = (TextView) findViewById(R.id.description);
        js_details = getIntent().getExtras().getString("json_device_one");
        tvDeviceDetails.setText(js_details);
        bBook = (Button) findViewById(R.id.bBook);
        btMain = (ImageButton) findViewById(R.id.btMain);
        bBook.setOnClickListener(this);
        btMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bBook:
                new Booking_status().execute(device_name);

                //startActivity(new Intent(this, ChooseADate.class));
              /*  Intent intent = new Intent(Showdevice.this, BookDevice.class);
                intent.putExtra("book_email",user_email);
                intent.putExtra("book_device",device_name);
                startActivity(intent);

             */
                break;
            case R.id.btMain:
                startActivity(new Intent(this, MainActivity.class));
                break;
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
            //TextView textView = (TextView)findViewById(R.id.description);
            //textView.setText(result);
        }
    }    //Asyntask for search


    class Booking_status extends AsyncTask<String,Void,String>
    {
        String Book_S_url;
        @Override
        protected void onPreExecute() {
            Book_S_url = "http://devicebooking.site88.net/Json_read_status.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String device = params[0];
            try {
                URL url = new URL(Book_S_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("device","UTF-8") + "=" + URLEncoder.encode(device,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String temp = "";

                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            js_status = result;
            Intent intent = new Intent(Showdevice.this,BookDevice.class);
            intent.putExtra("book_email",user_email);
            intent.putExtra("book_device",device_name);
            intent.putExtra("check_book",js_status);
            startActivity(intent);
            //  Toast.makeText(Showdevice.this,js_status,Toast.LENGTH_LONG).show();
        }
    }
}
