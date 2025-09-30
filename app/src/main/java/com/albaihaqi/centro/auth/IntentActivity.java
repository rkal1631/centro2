package com.albaihaqi.centro.auth;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.albaihaqi.centro.R;

public class IntentActivity extends AppCompatActivity {

    private TextView txtFullname, txtUsername, txtEmail, txtPassword,
            txtStudyProgram, txtDate, txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);


        txtFullname = findViewById(R.id.txt_get_fullname);
        txtUsername = findViewById(R.id.txt_get_username);
        txtEmail = findViewById(R.id.txt_get_email);
        txtPassword = findViewById(R.id.txt_get_password);
        txtStudyProgram = findViewById(R.id.txt_get_sp);
        txtDate = findViewById(R.id.txt_get_date);
        txtAddress = findViewById(R.id.txt_get_address);


        String fullname = getIntent().getStringExtra("fullname");
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String studyProgram = getIntent().getStringExtra("studyProgram");
        String date = getIntent().getStringExtra("date");
        String address = getIntent().getStringExtra("address");


        txtFullname.setText(fullname);
        txtUsername.setText(username);
        txtEmail.setText(email);
        txtPassword.setText(password);
        txtStudyProgram.setText(studyProgram);
        txtDate.setText(date);
        txtAddress.setText(address);
    }
}
