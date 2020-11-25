package com.example.mprlab;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private Button reg_bt;
    private EditText uname;
    private EditText email;
    private EditText pwd;
    private EditText conf_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        uname = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        conf_pwd = findViewById(R.id.confirm_password);

        reg_bt = findViewById(R.id.register);
        reg_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boolean fault = false;

                String checker = uname.getText().toString().trim();
                if (TextUtils.isEmpty(checker)){
                    uname.setError("Username cannot be empty!");
                    fault = true;
                }

                checker = email.getText().toString().trim();
                if (TextUtils.isEmpty(checker)){
                    uname.setError("Username cannot be empty!");
                    fault = true;
                }

                checker = pwd.getText().toString().trim();
                if (TextUtils.isEmpty(checker)){
                    pwd.setError("Password cannot be empty!");
                    fault = true;
                }

                checker = conf_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(checker)){
                    conf_pwd.setError("Password cannot be empty!");
                    fault = true;
                }

                if (!pwd.getText().toString().equals(conf_pwd.getText().toString())){
                    pwd.setError("Password does not match!");
                    conf_pwd.setError("Password does not match!");
                    fault = true;
                }

                if(fault){
                    return;
                }

                Intent i = new Intent(Register.this, MainActivity.class);
                i.putExtra("code","10");
                startActivity(i);
            }
        });

    }
}
