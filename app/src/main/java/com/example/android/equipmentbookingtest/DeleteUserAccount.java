package com.example.android.equipmentbookingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class DeleteUserAccount extends AppCompatActivity implements View.OnClickListener{

    Button btYes, btCancel;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_account);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int)(height * .6));

        btYes= (Button) findViewById(R.id.btyes);
        btCancel= (Button) findViewById(R.id.btcancel);

        myDb = new DatabaseHelper(this);

        btYes.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btyes:

                String method = "deleteAccount";
                String email = myDb.getUserEmail();


                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(method, email);

                myDb.setUserLoggedIn(email,"False");
                startActivity(new Intent(this,Login.class));

                break;
            case R.id.btcancel:
                startActivity(new Intent(DeleteUserAccount.this,AccountSetting.class));
        }
    }
}
