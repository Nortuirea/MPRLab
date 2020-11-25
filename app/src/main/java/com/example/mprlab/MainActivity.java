package com.example.mprlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tview;
    private EditText uname;
    private EditText pwd;
    private Button reg_bt;
    private Button login_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);

        tview = findViewById(R.id.action_indicator);

        String access_code = "";
        Bundle b = getIntent().getExtras();
        if (b != null){
            access_code = b.getString("code");
        }
        if (access_code.equals("10")){
            tview.setText("You just completed registration action");
        }

        reg_bt = findViewById(R.id.register);
        reg_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });

        login_bt = findViewById(R.id.login);
        login_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean login_fault = false;

                String checker = uname.getText().toString().trim();
                if (TextUtils.isEmpty(checker)){
                    uname.setError("Username cannot be empty!");
                    login_fault = true;
                }

                checker = pwd.getText().toString().trim();
                if (TextUtils.isEmpty(checker)){
                    pwd.setError("Password cannot be empty!");
                    login_fault = true;
                }

                if(login_fault){
                    return;
                }

                tview.setText("You just completed login action");
            }
        });
    }

}