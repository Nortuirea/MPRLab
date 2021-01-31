/*

Username    : asd
Password    : asd
Email       : aaaa@aaaa.aaaa

 */

package com.example.mprlab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private EditText email;
    private EditText pwd;
    private Button reg_bt;
    private Button login_bt;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            Intent i = new Intent(MainActivity.this, home.class);
            startActivity(i);
        }


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://mprlab-3f9e3-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("User");
        firebaseUser = firebaseAuth.getCurrentUser();

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent i = new Intent(MainActivity.this, home.class);
            startActivity(i);
        }
        catch (ApiException e) {
            Log.w("Log", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}