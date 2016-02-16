package com.example.android.equipmentbookingtest;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Login extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper myDb;
    Button bLogin, bRegister;
    EditText etEmail, etPassword;
    String login_email,login_password;
    int attempts = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);

        etEmail = (EditText) findViewById(R.id.username_edit_text);
        etPassword = (EditText) findViewById(R.id.password_edit_text);
        bLogin = (Button) findViewById(R.id.login_button);
        bRegister = (Button) findViewById(R.id. signup_button);

       // attempts.setText(Integer.toString(attempts));
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                String status_login = "";
                login_email = etEmail.getText().toString();
                login_password = etPassword.getText().toString();

                String psw = myDb.searchPassword(login_email);
               if(login_email.equals("")){
                    Toast.makeText(Login.this, "Email should not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(login_password.equals("")){
                    Toast.makeText(Login.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                }

                else{
                   BackgroundTask_login backgroundTask_login = new BackgroundTask_login();
                   backgroundTask_login.execute();
               }

                break;

            case R.id.signup_button:
                startActivity(new Intent(this, Register.class));
                break;

        }
    }

    //BackgroundAsynTask
    class BackgroundTask_login extends AsyncTask<Void, Void, String>
    {
        String login_url;
        @Override
        protected void onPreExecute() {
            login_url = "http://devicebooking.site88.net/Login.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_email", "UTF-8") + "=" + URLEncoder.encode(login_email,"UTF-8") + "&" +
                        URLEncoder.encode("login_password","UTF-8") + "=" + URLEncoder.encode(login_password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return line;

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
            if(result.equals("Login Success")) {
                String useremail = myDb.getUserEmail();
                if(useremail.equals("not found")){
                    myDb.insertData(login_email, login_email, login_password);
                }
                myDb.setUserLoggedIn(login_email, "True");
                Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                Intent to_main = new Intent(Login.this, MainActivity.class);
                startActivity(to_main);

            }
            else if(result.equals("Login Failed"))
            {
                Toast.makeText(Login.this,"Login Failed. User email or password is incorrect ",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("ok",null);
        dialogBuilder.show();
    }


}
