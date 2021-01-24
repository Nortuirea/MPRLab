package com.example.mprlab;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class Register extends AppCompatActivity {

    private Button reg_bt;
    private EditText uname;
    private EditText email;
    private EditText pwd;
    private EditText conf_pwd;
    final private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://mprlab-3f9e3-default-rtdb.firebaseio.com/");
    private DatabaseReference databaseReference = firebaseDatabase.getReference("User");

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

                String username = uname.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    uname.setError("Username cannot be empty!");
                    fault = true;
                }

                String mail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mail)){
                    email.setError("Username cannot be empty!");
                    fault = true;
                }

                String password = pwd.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    pwd.setError("Password cannot be empty!");
                    fault = true;
                }

                String conf_password = conf_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(conf_password)){
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

                String randomUID = UUID.randomUUID().toString();
                Data data = new Data(randomUID, username, mail, password);
                databaseReference.child(randomUID).setValue(data.toObject()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(Register.this, MainActivity.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

//                Intent i = new Intent(Register.this, MainActivity.class);
//                startActivity(i);
            }
        });

    }
}

