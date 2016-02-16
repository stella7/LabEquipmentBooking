package com.example.android.equipmentbookingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccountSetting extends AppCompatActivity {

    TextView tvchangepsw,tvdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        tvchangepsw = (TextView)findViewById(R.id.tvchangepsw);
        tvdelete = (TextView)findViewById(R.id.delet_account);

        tvchangepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSetting.this,ChangePassword.class));
            }
        });

        tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSetting.this,DeleteUserAccount.class));
            }
        });

    }

}
