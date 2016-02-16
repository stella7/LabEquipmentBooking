package com.example.android.equipmentbookingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDevice extends AppCompatActivity implements View.OnClickListener{

    TextView tvuser,tvdevice;
    TextView tvuserEmail1, tvuserEmail2, tvuserEmail3, tvuserEmail4, tvuserEmail5;
    CheckBox cbtimeEight, cbtimeTen, cbtimeFourteen, cbtimeSixteen, cbtimeTwenty;
    Button btDone;
    TextView tvNew;
    String user,device;
    String json_check;
    JSONObject jsonObject;
    JSONArray jsonArray;

    DatabaseHelper myDb;

    //String userEmail = getIntent().getExtras().getString("userEmail");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_device);

       // device = getIntent().getExtras().getString("book_device");
        readBookedfromServer();

        tvNew = (TextView)findViewById(R.id.username_newBook);
        cbtimeEight = (CheckBox) findViewById(R.id.checkBox1);
        cbtimeTen = (CheckBox) findViewById(R.id.checkBox2);
        cbtimeFourteen = (CheckBox) findViewById(R.id.checkBox3);
        cbtimeSixteen = (CheckBox) findViewById(R.id.checkBox4);
        cbtimeTwenty = (CheckBox) findViewById(R.id.checkBox5);
        tvuserEmail1 = (TextView) findViewById(R.id.user_email1);
        tvuserEmail2 = (TextView) findViewById(R.id.user_email2);
        tvuserEmail3 = (TextView) findViewById(R.id.user_email3);
        tvuserEmail4 = (TextView) findViewById(R.id.user_email4);
        tvuserEmail5 = (TextView) findViewById(R.id.user_email5);

        myDb = new DatabaseHelper(this);
        String dbUser = myDb.getUserEmail();
       // user = getIntent().getExtras().getString("book_email");
        device = getIntent().getExtras().getString("book_device");
        json_check = getIntent().getExtras().getString("check_book");

        tvuser = (TextView)findViewById(R.id.book_email);
        tvuser.setText(dbUser);
        tvdevice = (TextView)findViewById(R.id.book_device);
        tvdevice.setText(device);

        myDb = new DatabaseHelper(this);
        tvNew.setOnClickListener(this);
        btDone = (Button) findViewById(R.id.btDone);

     //   cbtimeEight.setOnClickListener(checkboxClickListener);
     //   cbtimeTen.setOnClickListener(checkboxClickListener);

/*
        tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookDevice.this, MainActivity.class));
            }
        });

*/
        try {
            jsonObject = new JSONObject(json_check);
            jsonArray = jsonObject.getJSONArray("book_status");
            int count = 0;
            String time, username;

            while(count < jsonArray.length())
            {
                JSONObject JO_STATUS = jsonArray.getJSONObject(count);
                time = JO_STATUS.getString("time");
                username = JO_STATUS.getString("username");
                // Toast.makeText(this, count + username, Toast.LENGTH_LONG).show();
                if(username.isEmpty() == false)     // the device is booked
                {
                    // Toast.makeText(this, count + username, Toast.LENGTH_LONG).show();
                    if (time.equals("08:30-10:30"))
                    {
                        cbtimeEight.setEnabled(false);
                        tvuserEmail1.setText(username);
                    }
                    else if(time.equals("10:30-12:30"))
                    {
                        cbtimeTen.setEnabled(false);
                        tvuserEmail2.setText(username);
                    }
                    else if(time.equals("14:30-16:30"))
                    {
                        cbtimeFourteen.setEnabled(false);
                        tvuserEmail3.setText(username);
                    }
                    else if(time.equals("16:30-18:30"))
                    {
                        cbtimeSixteen.setEnabled(false);
                        tvuserEmail4.setText(username);
                    }
                    else if(time.equals("20:30-22:30"))
                    {
                        cbtimeTwenty.setEnabled(false);
                        tvuserEmail5.setText(username);
                    }
                }
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btDone.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.username_newBook:
                startActivity(new Intent(BookDevice.this, MainActivity.class));
                break;
            case R.id.btDone:
                String username = myDb.getUserEmail();
                String time;
                String method = "AddBooking";

                if(cbtimeEight.isChecked()){
                    cbtimeEight.setEnabled(false);
                    tvuserEmail1.setText(username);
                    time = "08:30-10:30";
                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    backgroundTask.execute(method, device, time, username);

                }
                if(cbtimeTen.isChecked()){
                    cbtimeTen.setEnabled(false);
                    tvuserEmail2.setText(username);
                    time = "10:30-12:30";
                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    backgroundTask.execute(method, device, time, username);
                }
                if(cbtimeFourteen.isChecked()){
                    cbtimeFourteen.setEnabled(false);
                    tvuserEmail3.setText(username);
                    time = "14:30-16:30";
                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    backgroundTask.execute(method, device, time, username);
                }
                if(cbtimeSixteen.isChecked()){
                    cbtimeSixteen.setEnabled(false);
                    tvuserEmail4.setText(username);
                    time = "16:30-18:30";
                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    backgroundTask.execute(method, device, time, username);
                }
                if(cbtimeTwenty.isChecked()){
                    cbtimeTwenty.setEnabled(false);
                    tvuserEmail5.setText(username);
                    time = "20:30-22:30";
                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    backgroundTask.execute(method, device, time, username);
                }

        }
    }


    private void readBookedfromServer(){

    }

}
