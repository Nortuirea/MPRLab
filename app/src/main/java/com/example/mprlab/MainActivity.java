/*

Username    : asd
Password    : asd
Email       : aaaa@aaaa.aaaa

 */

package com.example.mprlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pwd;
    private Button reg_bt;
    private Button login_bt;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mprlab-3f9e3-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("User");
        firebaseUser = firebaseAuth.getCurrentUser();

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);

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

                final String mail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mail)){
                    email.setError("Email cannot be empty!");
                    login_fault = true;
                }

                final String password = pwd.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    pwd.setError("Password cannot be empty!");
                    login_fault = true;
                }

                if(login_fault){
                    return;
                }

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Data data = postSnapshot.getValue(Data.class);
                            if (mail.equals(data.email) && password.equals(data.password)){
                                Intent i = new Intent(MainActivity.this, home.class);
                                startActivity(i);
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Email or Password is wrong", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                databaseReference.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }

}