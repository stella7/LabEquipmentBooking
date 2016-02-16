package com.example.android.equipmentbookingtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper myDb;
    Button bSearch;
    EditText etDevice;
    TextView bViewall,etAccount;
    String name;
    String details;
    String Json_read_device;
    String Json_Booking;
    String username;
//    String Json_read_one;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img= (ImageView) findViewById(R.id.main_image);
        img.setImageResource(R.drawable.list);

        etDevice = (EditText) findViewById(R.id.device_edit_text);
        etAccount = (TextView) findViewById(R.id.username_edit_text);
        bSearch = (Button) findViewById(R.id.search_button);
        //bLogout = (Button) findViewById(R.id.logout_button);
        bViewall = (TextView) findViewById(R.id.viewall_button);

       // new BackgroundAsynTask().execute();

        bSearch.setOnClickListener(this);
        //bLogout.setOnClickListener(this);
        bViewall.setOnClickListener(this);
        //etAccount.setOnClickListener(this);

        myDb = new DatabaseHelper(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        new BackgroundAsynTask().execute();
        if (authenticate() == true){
            displayUserDetails();
        }
        else{
            startActivity(new Intent(MainActivity.this, Login.class));
       }
    }

    private boolean authenticate(){
        return myDb.checkLoggedIn();
    }

    private  void displayUserDetails(){
        //String user = myDb.getUserEmail();
        username = myDb.getUserEmail();
        etAccount.setText(username);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search_button:

                name = etDevice.getText().toString();
               // String userEmail = etAccount.getText().toString();
                new BackgroundTaskOne().execute(name);
/*
                if (details == null)
                {
                    Toast.makeText(this,"Please wait..",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(this, Showdevice.class);
                    intent.putExtra("deviceName", name);
                    intent.putExtra("userEmail", userEmail);
                    intent.putExtra("json_device_one", details);
                    startActivity(intent);
                }
*/
                //else{Toast.makeText(Login.this, "No such equipment", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.viewall_button:
                if(Json_read_device == null)
                {
                    Toast.makeText(getApplicationContext(), "Connecting...Please try later", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent_list = new Intent(this, Devicelist.class);
                    intent_list.putExtra("json_device_data",Json_read_device);
                    startActivity(intent_list);
                }

                break;
        }
    }

    //BackgroundAsynTask
    class BackgroundAsynTask extends AsyncTask<Void, Void, String>
    {
        String Json_r_de;
        String json_d_url;
        @Override
        protected void onPreExecute() {
            json_d_url = "http://devicebooking.site88.net/Json_read_Device.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(json_d_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((Json_r_de = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(Json_r_de + "\n");
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
            Json_read_device = result;
            // bViewall.setText(result);
        }
    }   //BackgroundAsynTask

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
                Toast.makeText(MainActivity.this,"No such device in system",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(MainActivity.this, Showdevice.class);
                intent.putExtra("deviceName", name);
                //intent.putExtra("userEmail", userEmail);
                intent.putExtra("json_device_one", details);
                startActivity(intent);
            }
            //TextView textView = (TextView)findViewById(R.id.description);
            //textView.setText(result);
        }
    }    //Asyntask for search

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_setting:

                startActivity(new Intent(this, AccountSetting.class));
                break;
            case R.id.action_history:
                new Back_history().execute(username);

            /*
                if (Json_Booking == null)
                {
                    Toast.makeText(MainActivity.this,"Please click again",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent history = new Intent(this, Useraccount.class);
                    history.putExtra("booking", Json_Booking);
                    startActivity(history);
                }
            */
                break;
            case R.id.action_contact:
                startActivity(new Intent(this, ContactUs.class));
                break;
            case R.id.action_logout:
                String email = myDb.getUserEmail();
                myDb.setUserLoggedIn(email,"False");
                startActivity(new Intent(this, Login.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //Background thread for view history
    class Back_history extends AsyncTask<String,Void,String>
    {
        String json_book_url;
       // String username;
        @Override
        protected void onPreExecute() {
            json_book_url = "http://devicebooking.site88.net/Json_read_Booking.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            try {
                URL url = new URL(json_book_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String temp = "";

                while((temp = bufferedReader.readLine()) != null)
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
            Json_Booking = result;

            if (Json_Booking == null)
            {
                Toast.makeText(MainActivity.this,"Please click again",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent history = new Intent(MainActivity.this, Useraccount.class);
                history.putExtra("booking", Json_Booking);
                startActivity(history);
            }
            //  Toast.makeText(MainActivity.this,Json_Booking,Toast.LENGTH_LONG).show();
        }
    }  // History Back thread

}
