package com.example.android.equipmentbookingtest;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{

    EditText etNewpsw, etConpsw;
    Button btChange;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        myDb = new DatabaseHelper(this);
        etNewpsw = (EditText)findViewById(R.id.new_psw);
        etConpsw = (EditText)findViewById(R.id.con_psw);
        btChange = (Button)findViewById(R.id.btchange);

        btChange.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String method = "ChangePassword";

        String email = myDb.getUserEmail();
        String password = etNewpsw.getText().toString();
        String con_psw = etConpsw.getText().toString();

        if(password.equals(con_psw)){

            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, email, password);


        }

        else{
            Toast.makeText(ChangePassword.this,"Two passwords should be the same", Toast.LENGTH_LONG).show();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
