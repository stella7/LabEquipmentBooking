package com.example.android.equipmentbookingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper myDb;
    Button bRegister;
    EditText etUsername, etEmail, etPassword, etConfmpsw;
    TextView tLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);

        etUsername = (EditText) findViewById(R.id.username_edit_text);
        etEmail = (EditText) findViewById(R.id.email_edit_text);
        etPassword = (EditText) findViewById(R.id.password_edit_text);
        etConfmpsw = (EditText) findViewById(R.id.confirmpsw_edit_text);
        bRegister = (Button) findViewById(R.id.signin_button);
        tLogin = (TextView) findViewById(R.id.login_text);

        bRegister.setOnClickListener(this);
        tLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signin_button:
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String c_password = etConfmpsw.getText().toString();

                String method = "register";

                if(username.equals("")){
                    Toast.makeText(Register.this, "Username should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(email.equals("")){
                    Toast.makeText(Register.this, "Email should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(Register.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(c_password.equals("")){
                    Toast.makeText(Register.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                }
                else if( !c_password.equals(password)){
                    Toast pass = Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_SHORT);
                    pass.show();
                }else{
                    //Insert the details in database

                    BackgroundTask backgroundTask = new BackgroundTask(this);
                    backgroundTask.execute(method, email, username, password);

                    boolean isInserted = myDb.insertData(username, email, password);
                    if (isInserted == true) {
                        Toast.makeText(Register.this, "Register successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    } else {
                        Toast.makeText(Register.this, "Register failed", Toast.LENGTH_SHORT).show();
                    }



                    finish();
                }
                break;
            case R.id.login_text:
                startActivity(new Intent(this, Login.class));
                break;

        }
    }

}
