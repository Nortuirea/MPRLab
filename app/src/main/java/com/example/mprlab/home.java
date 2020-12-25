package com.example.mprlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);

        MenuItem search = menu.findItem(R.id.ab_search);
        // MenuItemCompat.GetActionView is deprecated
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ab_help:
                Toast.makeText(this, "You tapped help",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ab_setting:
                Toast.makeText(this, "You tapped setting",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ab_about:
                Toast.makeText(this, "Aplikasi MPR Oleh Irwanto, Yanuar, dan Gita",Toast.LENGTH_LONG).show();
                return true;
            case R.id.ab_logout:
                AlertDialog.Builder logout_alert = new AlertDialog.Builder(this);
                logout_alert.setMessage("Confirm Signing Out").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(home.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}